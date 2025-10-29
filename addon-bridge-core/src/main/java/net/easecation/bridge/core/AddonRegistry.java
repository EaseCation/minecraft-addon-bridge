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

    /**
     * 设置资源包推送功能。
     * 在所有插件包加载和注册完成后调用，用于配置资源包自动推送给玩家。
     *
     * @param deployedPacks 已部署的资源包列表
     * @param config 插件配置，包含推送策略设置
     * @param plugin 插件实例（通常是 PluginBase），用于注册事件监听器等
     */
    default void setupResourcePackPushing(List<DeployedPack> deployedPacks, BridgeConfig config, Object plugin) {
        // 默认实现：不做任何处理
        // 各平台适配器可以根据需要覆写此方法
    }
}

