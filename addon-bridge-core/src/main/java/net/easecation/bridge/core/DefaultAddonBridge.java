package net.easecation.bridge.core;

import java.io.File;
import java.util.List;

public final class DefaultAddonBridge implements AddonBridge {
    private final AddonParser parser;
    private final DependencyResolver deps;
    private final AddonRegistry registry;
    private final ResourcePackDeployer deployer;

    public DefaultAddonBridge(AddonParser parser,
                              DependencyResolver deps,
                              AddonRegistry registry,
                              ResourcePackDeployer deployer) {
        this.parser = parser;
        this.deps = deps;
        this.registry = registry;
        this.deployer = deployer;
    }

    @Override
    public void loadAndRegisterAll(File addonsRoot) throws Exception {
        List<AddonPack> packs = parser.scanAndParse(addonsRoot);
        List<AddonPack> order = deps.resolveOrder(packs);
        for (AddonPack p : order) {
            deployer.deploy(p);
            registry.registerItems(p.items());
            registry.registerBlocks(p.blocks());
            registry.registerEntities(p.entities());
            registry.registerRecipes(p.recipes());
        }
        // 所有注册完成后，执行平台特定的后处理逻辑
        registry.afterAllRegistrations();
    }
}

