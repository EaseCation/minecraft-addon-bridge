package net.easecation.bridge.pack;

import net.easecation.bridge.core.AddonPack;
import net.easecation.bridge.core.DeployedPack;
import net.easecation.bridge.core.ResourcePackDeployer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Minimal deployer: zip resourceFiles to dataDir/packs, compute sha1, write resource_packs.yml.
 * If there are no resources, still creates an empty zip for predictable output.
 */
public class GenericResourceDeployer implements ResourcePackDeployer {
    private final Path dataDir;
    private final Logger log;
    private final String baseUrl; // optional CDN base; if empty uses file URI

    public GenericResourceDeployer(Path dataDir, Logger log, String baseUrl) {
        this.dataDir = dataDir;
        this.log = log;
        this.baseUrl = baseUrl != null ? baseUrl : "";
    }

    @Override
    public List<DeployedPack> deploy(AddonPack pack) throws Exception {
        List<DeployedPack> out = new ArrayList<>();
        Path packsDir = dataDir.resolve("packs");
        Files.createDirectories(packsDir);

        String ts = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").format(LocalDateTime.now());
        String fileName = sanitize(pack.manifest().name()) + "-" + ts + ".zip";
        Path outZip = packsDir.resolve(fileName);

        Map<String, byte[]> files = pack.resourceFiles();
        ZipUtil.zipFromMap(files, outZip);
        String sha1 = Sha1.hex(outZip);

        String url = baseUrl.isEmpty() ? outZip.toUri().toString() : joinUrl(baseUrl, fileName);
        Path yaml = dataDir.resolve("resource_packs.yml");
        ResourcePacksYaml.updateOrAppend(yaml, url, sha1);

        log.info("Deployed resource pack: " + url + " sha1=" + sha1);
        out.add(new DeployedPack(url, sha1));
        return out;
    }

    private static String sanitize(String s) { return s.replaceAll("[^A-Za-z0-9_-]", "_"); }

    private static String joinUrl(String base, String file) {
        if (base.endsWith("/")) return base + file;
        return base + "/" + file;
    }
}

