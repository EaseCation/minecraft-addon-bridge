package net.easecation.bridge.runner;

import cn.nukkit.plugin.PluginBase;
import net.easecation.bridge.core.*;
import net.easecation.bridge.pack.GenericResourceDeployer;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public final class BridgeBootstrap extends PluginBase {
    private AddonBridge bridge;
    private BridgeConfig config;

    @Override
    public void onEnable() {
        var nukkitLogger = getLogger(); // Plugin logger (Nukkit type)
        try {
            // 保存并加载配置文件
            saveDefaultConfig();
            this.config = loadBridgeConfig();
            nukkitLogger.info("AddonBridge configuration loaded:");
            nukkitLogger.info("  Push resource packs: " + config.isPushResourcePacks());
            nukkitLogger.info("  Push behavior packs: " + config.isPushBehaviorPacks());
            nukkitLogger.info("  Force accept: " + config.isForceAccept());

            // 根据服务端品牌信息与类特征选择适配器
            AddonRegistry registry = AdapterFactory.detectRegistryOrNoop(getServer(), nukkitLogger);

            Path dataDir = getDataFolder().toPath();
            BridgeLogger logger = new NukkitLoggerAdapter(nukkitLogger);
            ResourcePackDeployer deployer = new GenericResourceDeployer(
                    dataDir,
                    logger,
                    config.getBaseUrl()  // 使用配置文件中的 base-url
            );

            this.bridge = new DefaultAddonBridge(
                    new AddonParser(logger), new DependencyResolver(), registry, deployer
            );

            File addonsRoot = dataDir.resolve("addons").toFile();
            if (!addonsRoot.exists()) {
                //noinspection ResultOfMethodCallIgnored
                addonsRoot.mkdirs();
            }

            nukkitLogger.debug("AddonBridge scanning: " + addonsRoot.getAbsolutePath());
            nukkitLogger.debug("  Directory exists: " + addonsRoot.exists());
            nukkitLogger.debug("  Is directory: " + addonsRoot.isDirectory());

            File[] files = addonsRoot.listFiles();
            if (files != null) {
                nukkitLogger.debug("  Files in directory: " + files.length);
                for (File f : files) {
                    nukkitLogger.debug("    - " + f.getName() + " (isDirectory=" + f.isDirectory() + ")");
                }
            } else {
                nukkitLogger.warning("  Cannot list files in directory");
            }

            // 加载并注册所有插件包，获取已部署的资源包列表
            List<DeployedPack> deployedPacks = bridge.loadAndRegisterAll(addonsRoot);
            nukkitLogger.info("AddonBridge: Total deployed packs: " + deployedPacks.size());

            // 设置资源包推送功能（各适配器根据平台能力自行决定是否实现）
            registry.setupResourcePackPushing(deployedPacks, config, this);

            // 注册管理命令
            getServer().getCommandMap().register("addonbridge", new AddonBridgeCommand(this));
            nukkitLogger.info("AddonBridge command registered");

            nukkitLogger.info("AddonBridge enabled.");
        } catch (Throwable t) {
            nukkitLogger.error("AddonBridge failed to enable: " + t.getMessage(), t);
        }
    }

    /**
     * Loads BridgeConfig from the Nukkit config file.
     *
     * @return BridgeConfig instance populated with values from config.yml
     */
    private BridgeConfig loadBridgeConfig() {
        BridgeConfig config = new BridgeConfig();

        // Load resource pack settings
        config.setPushResourcePacks(getConfig().getBoolean("resource-pack.push-resource-packs", true));
        config.setPushBehaviorPacks(getConfig().getBoolean("resource-pack.push-behavior-packs", false));
        config.setForceAccept(getConfig().getBoolean("resource-pack.force-accept", true));
        config.setPushPriority(getConfig().getInt("resource-pack.push-priority", 0));
        config.setBaseUrl(getConfig().getString("resource-pack.base-url", ""));

        // Load plugin behavior settings
        config.setAutoScan(getConfig().getBoolean("plugin.auto-scan", true));
        config.setVerboseLogging(getConfig().getBoolean("plugin.verbose-logging", false));

        return config;
    }

    /**
     * Gets the current configuration.
     *
     * @return Current BridgeConfig instance
     */
    public BridgeConfig getBridgeConfig() {
        return config;
    }
}
