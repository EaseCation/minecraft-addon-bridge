package net.easecation.bridge.adapter.pnx;

import net.easecation.bridge.core.*;

import java.util.List;

public class PnxRegistry implements AddonRegistry {
    private final BridgeLogger log;
    private static final Capabilities CAPS = new Capabilities(true);

    public PnxRegistry(BridgeLogger log) { this.log = log; }

    @Override public void registerItems(List<ItemDef> items) { log.info("[PNX] registerItems size=" + items.size()); }
    @Override public void registerBlocks(List<BlockDef> blocks) { log.info("[PNX] registerBlocks size=" + blocks.size()); }
    @Override public void registerEntities(List<EntityDef> entities) { log.info("[PNX] registerEntities size=" + entities.size()); }
    @Override public void registerRecipes(List<RecipeDef> recipes) { log.info("[PNX] registerRecipes size=" + recipes.size()); }
    @Override public Capabilities capabilities() { return CAPS; }

    @Override
    public void afterAllRegistrations() {
        // PNX 平台当前不需要特殊的后处理逻辑
        log.info("[PNX] Registration completed, no platform-specific post-processing needed");
    }
}

