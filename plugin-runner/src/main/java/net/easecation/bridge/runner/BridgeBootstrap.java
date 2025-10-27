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
        var pLog = getLogger(); // Plugin logger (Nukkit type)
        java.util.logging.Logger jLog = java.util.logging.Logger.getLogger(getName());
        try {
            // 根据服务端品牌信息与类特征选择适配器
            AddonRegistry registry = AdapterFactory.detectRegistryOrNoop(getServer(), jLog);

            Path dataDir = getDataFolder().toPath();
            ResourcePackDeployer deployer = new GenericResourceDeployer(
                    dataDir,
                    jLog,
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

            pLog.info("AddonBridge scanning: " + addonsRoot.getAbsolutePath());
            bridge.loadAndRegisterAll(addonsRoot);
            pLog.info("AddonBridge enabled.");
        } catch (Throwable t) {
            pLog.error("AddonBridge failed to enable: " + t.getMessage());
        }
    }
}
