package net.easecation.bridge.adapter.pnx;

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
 * PNX (PowerNukkitX) resource pack loader for AddonBridge.
 * Loads deployed resource packs at server startup.
 *
 * Note: PNX does not distinguish between client versions (Bedrock/NetEase),
 * so all resource packs are automatically compatible with all clients (universal support).
 */
public class PnxResourcePackLoader implements ResourcePackLoader {
    private final List<DeployedPack> deployedPacks;
    private final BridgeConfig config;
    private final BridgeLogger log;

    public PnxResourcePackLoader(List<DeployedPack> deployedPacks, BridgeConfig config, BridgeLogger log) {
        this.deployedPacks = deployedPacks;
        this.config = config;
        this.log = log;
    }

    @Override
    public List<ResourcePack> loadPacks() {
        List<ResourcePack> resourcePacks = new ArrayList<>();

        log.info("[PNX] Loading AddonBridge resource packs...");

        for (DeployedPack pack : deployedPacks) {
            // Filter based on pack type and configuration
            if (!config.shouldPushPack(pack.packType())) {
                log.debug("[PNX] Skipping pack (disabled in config): " + pack.url());
                continue;
            }

            try {
                // Convert file:// URL to File
                String url = pack.url();
                if (!url.startsWith("file://")) {
                    log.warning("[PNX] Non-file URL not supported for pack loading: " + url);
                    continue;
                }

                URI uri = new URI(url);
                File packFile = new File(uri);

                if (!packFile.exists()) {
                    log.error("[PNX] Pack file does not exist: " + packFile.getAbsolutePath());
                    continue;
                }

                log.debug("[PNX] Loading pack: " + packFile.getName());

                // Create ZippedResourcePack
                // Note: PNX's ZippedResourcePack does not have SupportType parameter
                // All packs are automatically universal (support all client versions)
                ZippedResourcePack resourcePack = new ZippedResourcePack(packFile);
                resourcePacks.add(resourcePack);

                log.info("[PNX] ✓ Loaded resource pack: " + packFile.getName() +
                        " (UUID: " + resourcePack.getPackId() + ", Version: " + resourcePack.getPackVersion() + ")");

            } catch (Exception e) {
                log.error("[PNX] ✗ Failed to load pack: " + pack.url());
                log.error("[PNX]   Error type: " + e.getClass().getSimpleName());
                log.error("[PNX]   Error message: " + e.getMessage());

                // Log stack trace
                log.error("[PNX]   Stack trace:");
                for (StackTraceElement element : e.getStackTrace()) {
                    log.error("[PNX]     at " + element.toString());
                    if (element.getClassName().contains("easecation.bridge")) {
                        break;
                    }
                }
            }
        }

        log.info("[PNX] Loaded " + resourcePacks.size() + " resource pack(s) for universal client support");

        return resourcePacks;
    }
}
