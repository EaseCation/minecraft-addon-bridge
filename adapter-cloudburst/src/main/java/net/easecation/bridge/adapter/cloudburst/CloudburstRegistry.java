package net.easecation.bridge.adapter.cloudburst;

import net.easecation.bridge.core.*;

import java.util.List;

public class CloudburstRegistry implements AddonRegistry {
    private final BridgeLogger log;
    private static final Capabilities CAPS = new Capabilities(true);

    public CloudburstRegistry(BridgeLogger log) { this.log = log; }

    @Override public void registerItems(List<ItemDef> items) {
        log.info("[Cloudburst] registerItems size=" + items.size());
    }
    @Override public void registerBlocks(List<BlockDef> blocks) {
        log.info("[Cloudburst] registerBlocks size=" + blocks.size());
    }
    @Override public void registerEntities(List<EntityDef> entities) {
        log.info("[Cloudburst] registerEntities size=" + entities.size());
    }
    @Override public void registerRecipes(List<RecipeDef> recipes) {
        log.info("[Cloudburst] registerRecipes size=" + recipes.size());
    }
    @Override public Capabilities capabilities() { return CAPS; }

    @Override
    public void afterAllRegistrations() {
        // Cloudburst 平台当前不需要特殊的后处理逻辑
        log.info("[Cloudburst] Registration completed, no platform-specific post-processing needed");
    }
}

