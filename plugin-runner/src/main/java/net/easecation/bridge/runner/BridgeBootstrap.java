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
    private AddonRegistry registry;
    private List<DeployedPack> deployedPacks;
    private File addonsRoot;

    @Override
    public void onLoad() {
        var nukkitLogger = getLogger();

        // 初始化全局Logger，必须在所有业务逻辑之前
        BridgeLoggerHolder.setLogger(new NukkitLoggerAdapter(nukkitLogger));

        try {
            nukkitLogger.info("AddonBridge loading...");

            // 1. 保存并加载配置文件
            saveDefaultConfig();
            this.config = loadBridgeConfig();
            nukkitLogger.info("AddonBridge configuration loaded:");
            nukkitLogger.info("  Push resource packs: " + config.isPushResourcePacks());
            nukkitLogger.info("  Push behavior packs: " + config.isPushBehaviorPacks());
            nukkitLogger.info("  Force accept: " + config.isForceAccept());

            // 2. 根据服务端品牌信息与类特征选择适配器
            this.registry = AdapterFactory.detectRegistryOrNoop(getServer(), nukkitLogger);

            // 2.5. 初始化Registry，传递Plugin实例
            this.registry.initialize(this);

            // 3. 初始化Bridge和资源部署器
            Path dataDir = getDataFolder().toPath();
            ResourcePackDeployer deployer = new GenericResourceDeployer(
                    dataDir,
                    config.getBaseUrl()  // 使用配置文件中的 base-url
            );

            this.bridge = new DefaultAddonBridge(
                    new AddonParser(), new DependencyResolver(), registry, deployer, config
            );

            // 4. 准备addons目录
            this.addonsRoot = dataDir.resolve("addons").toFile();
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

            // 5. 判断是否需要在onLoad阶段提前注册
            // 某些平台（如MOT）会在enablePlugins(STARTUP)后调用closeRegistry()，必须在onLoad中注册
            // 其他平台（如EaseCation）可以延迟到onEnable阶段注册，更符合插件生命周期
            if (registry.requiresEarlyRegistration()) {
                nukkitLogger.info("AddonBridge: Platform requires early registration (onLoad phase)");
                performRegistration(nukkitLogger);
            } else {
                nukkitLogger.info("AddonBridge: Deferring registration to onEnable phase (platform allows flexible timing)");
            }

            nukkitLogger.info("AddonBridge loaded successfully.");

        } catch (Throwable t) {
            nukkitLogger.error("AddonBridge failed to load: " + t.getMessage(), t);
            throw new RuntimeException("AddonBridge failed to load", t);
        }
    }

    @Override
    public void onEnable() {
        var nukkitLogger = getLogger();
        try {
            nukkitLogger.info("AddonBridge enabling...");

            // 如果在onLoad中没有注册（延迟注册模式），现在执行注册
            if (deployedPacks == null) {
                nukkitLogger.info("AddonBridge: Performing deferred registration (onEnable phase)");
                performRegistration(nukkitLogger);
            }

            // 设置资源包推送功能（各适配器根据平台能力自行决定是否实现）
            if (deployedPacks != null) {
                registry.setupResourcePackPushing(deployedPacks, config, this);
            }

            // 注册管理命令
            getServer().getCommandMap().register("addonbridge", new AddonBridgeCommand(this));
            nukkitLogger.info("AddonBridge command registered");

            nukkitLogger.info("AddonBridge enabled.");
        } catch (Throwable t) {
            nukkitLogger.error("AddonBridge failed to enable: " + t.getMessage(), t);
        }
    }

    /**
     * 执行实际的插件包扫描、解析和注册流程。
     * 该方法会在onLoad或onEnable中被调用，具体取决于平台适配器的要求。
     *
     * @param nukkitLogger Nukkit日志记录器
     * @throws Exception 如果注册过程中发生错误
     */
    private void performRegistration(cn.nukkit.utils.Logger nukkitLogger) throws Exception {
        this.deployedPacks = bridge.loadAndRegisterAll(addonsRoot);
        nukkitLogger.info("AddonBridge: Total deployed packs: " + deployedPacks.size());
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

        // Load custom content registration settings
        config.setRegisterBlocks(getConfig().getBoolean("plugin.register-blocks", true));
        config.setRegisterEntities(getConfig().getBoolean("plugin.register-entities", true));
        config.setRegisterItems(getConfig().getBoolean("plugin.register-items", true));

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
