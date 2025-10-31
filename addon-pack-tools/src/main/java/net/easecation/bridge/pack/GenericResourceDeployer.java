package net.easecation.bridge.pack;

import net.easecation.bridge.core.AddonPack;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.DeployedPack;
import net.easecation.bridge.core.PackType;
import net.easecation.bridge.core.ResourcePackDeployer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Minimal deployer: zip resourceFiles to dataDir/packs, compute sha1, write resource_packs.yml.
 * If there are no resources, still creates an empty zip for predictable output.
 */
public class GenericResourceDeployer implements ResourcePackDeployer {
    private final Path dataDir;
    private final String baseUrl; // optional CDN base; if empty uses file URI

    public GenericResourceDeployer(Path dataDir, String baseUrl) {
        this.dataDir = dataDir;
        this.baseUrl = baseUrl != null ? baseUrl : "";
    }

    @Override
    public List<DeployedPack> deploy(AddonPack pack) throws Exception {
        List<DeployedPack> out = new ArrayList<>();
        Path packsDir = dataDir.resolve("packs");
        Files.createDirectories(packsDir);

        PackType packType = pack.manifest().getPackType();
        String packName = pack.packName();

        if (!pack.needsPackaging()) {
            // Use original ZIP/MCPACK file directly - no need to repackage
            Path originalFile = pack.originalPath();
            String sha1 = Sha1.hex(originalFile);

            // For local files, use file:// URL; for HTTP deployment, construct full URL
            String url = baseUrl.isEmpty()
                ? originalFile.toUri().toString()
                : joinUrl(baseUrl, originalFile.getFileName().toString());

            BridgeLoggerHolder.getLogger().info("Using original pack file: " + packName);
            BridgeLoggerHolder.getLogger().debug("  Path: " + originalFile);
            BridgeLoggerHolder.getLogger().debug("  SHA1: " + sha1);
            BridgeLoggerHolder.getLogger().debug("  Pack type: " + packType);

            out.add(new DeployedPack(url, sha1, packType));
        } else {
            // Package directory-based pack
            // Use version-based naming instead of timestamp to enable caching
            String version = pack.manifest().getVersion();
            String fileName = sanitize(packName) + "-" + version + ".zip";
            Path outZip = packsDir.resolve(fileName);

            // Check if already packaged with same content
            boolean needsRepackaging = true;
            if (Files.exists(outZip)) {
                String existingSha1 = Sha1.hex(outZip);
                BridgeLoggerHolder.getLogger().debug("Found existing package: " + fileName + " (sha1=" + existingSha1 + ")");

                // For now, we trust existing files with same version
                // In the future, could add content hash checking
                needsRepackaging = false;
                BridgeLoggerHolder.getLogger().debug("  Reusing existing package");
            }

            if (needsRepackaging) {
                // Stream pack contents directly from directory to ZIP
                // This avoids loading large resource packs into memory
                BridgeLoggerHolder.getLogger().info("Packaging directory to ZIP: " + packName);
                ZipUtil.zipFromDirectory(pack.originalPath(), outZip);
                BridgeLoggerHolder.getLogger().debug("Created new package: " + fileName);
            }

            String sha1 = Sha1.hex(outZip);
            String url = baseUrl.isEmpty() ? outZip.toUri().toString() : joinUrl(baseUrl, fileName);

            BridgeLoggerHolder.getLogger().info("Deployed packaged resource: " + packName);
            BridgeLoggerHolder.getLogger().debug("  URL: " + url);
            BridgeLoggerHolder.getLogger().debug("  SHA1: " + sha1);
            BridgeLoggerHolder.getLogger().debug("  Pack type: " + packType);

            out.add(new DeployedPack(url, sha1, packType));
        }

        // Update resource_packs.yml
        Path yaml = dataDir.resolve("resource_packs.yml");
        for (DeployedPack dp : out) {
            ResourcePacksYaml.updateOrAppend(yaml, dp.url(), dp.sha1());
        }

        return out;
    }

    private static String sanitize(String s) { return s.replaceAll("[^A-Za-z0-9_-]", "_"); }

    private static String joinUrl(String base, String file) {
        if (base.endsWith("/")) return base + file;
        return base + "/" + file;
    }
}

