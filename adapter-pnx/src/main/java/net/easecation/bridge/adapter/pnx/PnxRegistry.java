package net.easecation.bridge.adapter.pnx;

import net.easecation.bridge.core.*;

import java.util.List;
import java.util.logging.Logger;

public class PnxRegistry implements AddonRegistry {
    private final Logger log;
    private static final Capabilities CAPS = new Capabilities(true);

    public PnxRegistry(Logger log) { this.log = log; }

    @Override public void registerItems(List<ItemDef> items) { log.info("[PNX] registerItems size=" + items.size()); }
    @Override public void registerBlocks(List<BlockDef> blocks) { log.info("[PNX] registerBlocks size=" + blocks.size()); }
    @Override public void registerEntities(List<EntityDef> entities) { log.info("[PNX] registerEntities size=" + entities.size()); }
    @Override public void registerRecipes(List<RecipeDef> recipes) { log.info("[PNX] registerRecipes size=" + recipes.size()); }
    @Override public Capabilities capabilities() { return CAPS; }
}

