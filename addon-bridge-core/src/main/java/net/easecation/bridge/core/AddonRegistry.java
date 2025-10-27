package net.easecation.bridge.core;

import java.util.List;

public interface AddonRegistry {
    void registerItems(List<ItemDef> items);
    void registerBlocks(List<BlockDef> blocks);
    void registerEntities(List<EntityDef> entities);
    void registerRecipes(List<RecipeDef> recipes);
    Capabilities capabilities();
}

