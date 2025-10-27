package net.easecation.bridge.adapter.pm1e;

import net.easecation.bridge.core.*;

import java.util.List;
import java.util.logging.Logger;

public class Pm1eRegistry implements AddonRegistry {
    private final Logger log;
    private static final Capabilities CAPS = new Capabilities(true);

    public Pm1eRegistry(Logger log) { this.log = log; }

    @Override public void registerItems(List<ItemDef> items) { log.info("[PM1E] registerItems size=" + items.size()); }
    @Override public void registerBlocks(List<BlockDef> blocks) { log.info("[PM1E] registerBlocks size=" + blocks.size()); }
    @Override public void registerEntities(List<EntityDef> entities) { log.info("[PM1E] registerEntities size=" + entities.size()); }
    @Override public void registerRecipes(List<RecipeDef> recipes) { log.info("[PM1E] registerRecipes size=" + recipes.size()); }
    @Override public Capabilities capabilities() { return CAPS; }
}

