package net.easecation.bridge.core;

import java.util.List;

public interface AddonRegistry {
    void registerItems(List<ItemDef> items);
    void registerBlocks(List<BlockDef> blocks);
    void registerEntities(List<EntityDef> entities);
    void registerRecipes(List<RecipeDef> recipes);
    Capabilities capabilities();

    /**
     * 在所有注册完成后调用，用于执行平台特定的后处理逻辑。
     * 例如：重建方块调色板、重建物品映射、缓存网络数据包等。
     *
     * 该方法会在 registerBlocks(), registerItems(), registerEntities()
     * 和 registerRecipes() 全部执行完毕后被调用。
     */
    void afterAllRegistrations();
}

