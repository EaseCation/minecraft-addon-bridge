package net.easecation.bridge.adapter.cloudburst;

import net.easecation.bridge.core.*;

import java.util.List;
import java.util.logging.Logger;

public class CloudburstRegistry implements AddonRegistry {
    private final Logger log;
    private static final Capabilities CAPS = new Capabilities(true);

    public CloudburstRegistry(Logger log) { this.log = log; }

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
}

