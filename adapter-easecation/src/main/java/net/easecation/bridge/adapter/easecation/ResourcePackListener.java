package net.easecation.bridge.adapter.easecation;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerRequestResourcePackEvent;
import cn.nukkit.resourcepacks.ZippedResourcePack;
import net.easecation.bridge.core.BridgeConfig;
import net.easecation.bridge.core.BridgeLogger;
import net.easecation.bridge.core.DeployedPack;

import java.io.File;
import java.util.List;

/**
 * 监听玩家请求资源包事件，自动推送已部署的资源包和行为包给玩家
 * Listens for player resource pack requests and automatically pushes deployed resource/behavior packs
 */
public class ResourcePackListener implements Listener {
    private final List<DeployedPack> deployedPacks;
    private final BridgeConfig config;
    private final BridgeLogger log;

    public ResourcePackListener(List<DeployedPack> deployedPacks, BridgeConfig config, BridgeLogger log) {
        this.deployedPacks = deployedPacks;
        this.config = config;
        this.log = log;
    }

    @EventHandler
    public void onRequestResourcePack(PlayerRequestResourcePackEvent event) {
        if (deployedPacks.isEmpty()) {
            return;
        }

        log.debug("[ResourcePack] Player " + event.getPlayer().getName() + " requesting resource packs");
        log.debug("[ResourcePack] Total deployed packs: " + deployedPacks.size());

        int successCount = 0;
        int failureCount = 0;
        int skippedCount = 0;

        for (DeployedPack pack : deployedPacks) {
            // Check if this pack should be pushed based on its type and configuration
            if (!config.shouldPushPack(pack.packType())) {
                log.debug("[ResourcePack] ⊘ Skipping " + pack.packType() + " pack (disabled in config): " + pack.url());
                skippedCount++;
                continue;
            }

            try {
                // 从 URL 中提取文件路径
                // file:///path/to/pack.zip -> /path/to/pack.zip
                String url = pack.url();
                if (!url.startsWith("file://")) {
                    // 如果使用的是HTTP URL，Nukkit可能不直接支持
                    // 这种情况下客户端会尝试从URL下载
                    log.warning("[ResourcePack] HTTP URL detected: " + url);
                    log.warning("[ResourcePack] Nukkit may not support pushing HTTP URLs directly");
                    failureCount++;
                    continue;
                }

                // 移除 file:// 前缀获取本地文件路径
                String filePath = url.substring(7); // "file://".length() = 7
                File packFile = new File(filePath);

                if (!packFile.exists()) {
                    log.warning("[ResourcePack] Pack file not found: " + filePath);
                    failureCount++;
                    continue;
                }

                // 创建 ZippedResourcePack 对象
                // 第二个参数 false 表示不强制客户端必须接受该资源包
                ZippedResourcePack resourcePack = new ZippedResourcePack(packFile, false);

                // 推送资源包给玩家
                event.putResourcePack(resourcePack);

                log.debug("[ResourcePack] ✓ Pushed pack: " + resourcePack.getPackName() +
                        " (id=" + resourcePack.getPackId() + ", version=" + resourcePack.getPackVersion() + ")");
                successCount++;

            } catch (Exception e) {
                log.error("[ResourcePack] ✗ Failed to push pack: " + pack.url());
                log.error("[ResourcePack]   Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                failureCount++;
            }
        }

        // Apply force accept configuration
        if (config.isForceAccept()) {
            event.setMustAccept(true);
            log.debug("[ResourcePack] Force accept enabled - players must accept packs to join");
        }

        log.info("[ResourcePack] Push completed - Success: " + successCount + ", Failed: " + failureCount + ", Skipped: " + skippedCount);
    }
}
