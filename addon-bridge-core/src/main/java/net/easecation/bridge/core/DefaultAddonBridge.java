package net.easecation.bridge.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class DefaultAddonBridge implements AddonBridge {
    private final AddonParser parser;
    private final DependencyResolver deps;
    private final AddonRegistry registry;
    private final ResourcePackDeployer deployer;
    private final BridgeConfig config;

    public DefaultAddonBridge(AddonParser parser,
                              DependencyResolver deps,
                              AddonRegistry registry,
                              ResourcePackDeployer deployer,
                              BridgeConfig config) {
        this.parser = parser;
        this.deps = deps;
        this.registry = registry;
        this.deployer = deployer;
        this.config = config;
    }

    @Override
    public List<DeployedPack> loadAndRegisterAll(File addonsRoot) throws Exception {
        List<AddonPack> packs = parser.scanAndParse(addonsRoot);
        List<AddonPack> order = deps.resolveOrder(packs);

        // 收集所有已部署的资源包
        List<DeployedPack> allDeployedPacks = new ArrayList<>();

        for (AddonPack p : order) {
            List<DeployedPack> deployed = deployer.deploy(p);
            allDeployedPacks.addAll(deployed);

            // 纯资源包不需要服务端注册，只有行为包（或混合包）才需要
            if (p.manifest().isBehaviorPack()) {
                // 根据配置决定是否注册各类自定义内容
                if (config.isRegisterItems()) {
                    registry.registerItems(p.items());
                }
                if (config.isRegisterBlocks()) {
                    registry.registerBlocks(p.blocks());
                }
                if (config.isRegisterEntities()) {
                    registry.registerEntities(p.entities());
                }
                registry.registerRecipes(p.recipes());
            }
        }

        // 所有注册完成后，执行平台特定的后处理逻辑
        registry.afterAllRegistrations();

        // 返回所有已部署的资源包，供资源包推送使用
        return allDeployedPacks;
    }
}

