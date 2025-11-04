package net.easecation.bridge.adapter.pm1e;

import cn.nukkit.resourcepacks.ResourcePack;
import cn.nukkit.resourcepacks.ZippedResourcePack;
import cn.nukkit.resourcepacks.loader.ResourcePackLoader;
import net.easecation.bridge.core.BridgeConfig;
import net.easecation.bridge.core.BridgeLogger;
import net.easecation.bridge.core.DeployedPack;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * PM1E (PowerNukkit) resource pack loader for AddonBridge.
 * Loads deployed resource packs at server startup.
 */
public class Pm1eResourcePackLoader implements ResourcePackLoader {
    private final List<DeployedPack> deployedPacks;
    private final BridgeConfig config;
    private final BridgeLogger log;

    public Pm1eResourcePackLoader(List<DeployedPack> deployedPacks, BridgeConfig config, BridgeLogger log) {
        this.deployedPacks = deployedPacks;
        this.config = config;
        this.log = log;
    }

    @Override
    public List<ResourcePack> loadPacks() {
        List<ResourcePack> resourcePacks = new ArrayList<>();

        log.info("[PM1E] Loading AddonBridge resource packs...");

        for (DeployedPack pack : deployedPacks) {
            // Filter based on pack type and configuration
            if (!config.shouldPushPack(pack.packType())) {
                log.debug("[PM1E] Skipping pack (disabled in config): " + pack.url());
                continue;
            }

            try {
                // Convert file:// URL to File
                String url = pack.url();
                if (!url.startsWith("file://")) {
                    log.warning("[PM1E] Non-file URL not supported for pack loading: " + url);
                    continue;
                }

                URI uri = new URI(url);
                File packFile = new File(uri);

                if (!packFile.exists()) {
                    log.error("[PM1E] Pack file does not exist: " + packFile.getAbsolutePath());
                    continue;
                }

                log.debug("[PM1E] Loading pack: " + packFile.getName());

                // Create ZippedResourcePack
                ZippedResourcePack resourcePack = new ZippedResourcePack(packFile);
                resourcePacks.add(resourcePack);

                log.info("[PM1E] ✓ Loaded resource pack: " + packFile.getName() +
                        " (UUID: " + resourcePack.getPackId() + ", Version: " + resourcePack.getPackVersion() + ")");

            } catch (Exception e) {
                log.error("[PM1E] ✗ Failed to load pack: " + pack.url());
                log.error("[PM1E]   Error type: " + e.getClass().getSimpleName());
                log.error("[PM1E]   Error message: " + e.getMessage());

                // Log stack trace
                log.error("[PM1E]   Stack trace:");
                for (StackTraceElement element : e.getStackTrace()) {
                    log.error("[PM1E]     at " + element.toString());
                    if (element.getClassName().contains("easecation.bridge")) {
                        break;
                    }
                }
            }
        }

        log.info("[PM1E] Loaded " + resourcePacks.size() + " resource pack(s)");

        return resourcePacks;
    }
}
