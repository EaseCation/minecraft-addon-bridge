package net.easecation.bridge.core;

import java.util.List;

/** A no-op registry for offline/local builds. */
public class NoopRegistry implements AddonRegistry {
    private static final Capabilities CAPS = new Capabilities(true);

    @Override public void registerItems(List<ItemDef> items) { /* no-op */ }
    @Override public void registerBlocks(List<BlockDef> blocks) { /* no-op */ }
    @Override public void registerEntities(List<EntityDef> entities) { /* no-op */ }
    @Override public void registerRecipes(List<RecipeDef> recipes) { /* no-op */ }
    @Override public Capabilities capabilities() { return CAPS; }
    @Override public void afterAllRegistrations() { /* no-op */ }
}

