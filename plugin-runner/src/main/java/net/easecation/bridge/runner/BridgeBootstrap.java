package net.easecation.bridge.runner;

import cn.nukkit.plugin.PluginBase;
import net.easecation.bridge.core.*;
import net.easecation.bridge.pack.GenericResourceDeployer;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public final class BridgeBootstrap extends PluginBase {
    private AddonBridge bridge;

    @Override
    public void onEnable() {
        var nukkitLogger = getLogger(); // Plugin logger (Nukkit type)
        try {
            // 根据服务端品牌信息与类特征选择适配器
            AddonRegistry registry = AdapterFactory.detectRegistryOrNoop(getServer(), nukkitLogger);

            Path dataDir = getDataFolder().toPath();
            ResourcePackDeployer deployer = new GenericResourceDeployer(
                    dataDir,
                    new NukkitLoggerAdapter(nukkitLogger),
                    System.getenv("BRIDGE_RP_BASE_URL")
            );

            this.bridge = new DefaultAddonBridge(
                    new AddonParser(), new DependencyResolver(), registry, deployer
            );

            File addonsRoot = dataDir.resolve("addons").toFile();
            if (!addonsRoot.exists()) {
                //noinspection ResultOfMethodCallIgnored
                addonsRoot.mkdirs();
            }

            nukkitLogger.info("AddonBridge scanning: " + addonsRoot.getAbsolutePath());
            nukkitLogger.info("  Directory exists: " + addonsRoot.exists());
            nukkitLogger.info("  Is directory: " + addonsRoot.isDirectory());

            File[] files = addonsRoot.listFiles();
            if (files != null) {
                nukkitLogger.info("  Files in directory: " + files.length);
                for (File f : files) {
                    nukkitLogger.info("    - " + f.getName() + " (isDirectory=" + f.isDirectory() + ")");
                }
            } else {
                nukkitLogger.warning("  Cannot list files in directory");
            }

            // 加载并注册所有插件包，获取已部署的资源包列表
            List<DeployedPack> deployedPacks = bridge.loadAndRegisterAll(addonsRoot);
            nukkitLogger.info("AddonBridge: Total deployed packs: " + deployedPacks.size());

            // 设置资源包推送功能（各适配器根据平台能力自行决定是否实现）
            registry.setupResourcePackPushing(deployedPacks, this);

            nukkitLogger.info("AddonBridge enabled.");
        } catch (Throwable t) {
            nukkitLogger.error("AddonBridge failed to enable: " + t.getMessage(), t);
        }
    }
}
