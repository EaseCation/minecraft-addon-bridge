package net.easecation.bridge.adapter.mot;

import net.easecation.bridge.core.*;

import java.util.List;

public class MotRegistry implements AddonRegistry {
    private final BridgeLogger log;
    private static final Capabilities CAPS = new Capabilities(true);

    public MotRegistry(BridgeLogger log) { this.log = log; }

    @Override public void registerItems(List<ItemDef> items) { log.info("[MOT] registerItems size=" + items.size()); }
    @Override public void registerBlocks(List<BlockDef> blocks) { log.info("[MOT] registerBlocks size=" + blocks.size()); }
    @Override public void registerEntities(List<EntityDef> entities) { log.info("[MOT] registerEntities size=" + entities.size()); }
    @Override public void registerRecipes(List<RecipeDef> recipes) { log.info("[MOT] registerRecipes size=" + recipes.size()); }
    @Override public Capabilities capabilities() { return CAPS; }

    @Override
    public void afterAllRegistrations() {
        // MOT 平台当前不需要特殊的后处理逻辑
        log.info("[MOT] Registration completed, no platform-specific post-processing needed");
    }
}

