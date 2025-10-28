package net.easecation.bridge.runner;

import cn.nukkit.plugin.PluginBase;
import net.easecation.bridge.core.*;
import net.easecation.bridge.pack.GenericResourceDeployer;

import java.io.File;
import java.nio.file.Path;

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
            bridge.loadAndRegisterAll(addonsRoot);
            nukkitLogger.info("AddonBridge enabled.");
        } catch (Throwable t) {
            nukkitLogger.error("AddonBridge failed to enable: " + t.getMessage(), t);
        }
    }
}
