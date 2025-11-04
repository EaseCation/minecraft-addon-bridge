package net.easecation.bridge.core;

import java.util.List;

public interface AddonRegistry {
    /**
     * 初始化Registry，传递Plugin实例。
     * 该方法会在Registry创建后、注册方法调用前被调用。
     *
     * @param plugin 插件实例（通常是 PluginBase）
     */
    default void initialize(Object plugin) {
        // 默认实现：不做任何处理
    }

    void registerItems(List<ItemDef> items);
    void registerBlocks(List<BlockDef> blocks);
    void registerEntities(List<EntityDef> entities);
    void registerRecipes(List<RecipeDef> recipes);
    Capabilities capabilities();

    /**
     * 判断当前适配器是否需要在插件的onLoad阶段提前注册内容。
     * 某些平台（如MOT）会在enablePlugins(STARTUP)后调用closeRegistry()，
     * 因此必须在onLoad阶段完成所有注册。
     *
     * @return true 表示必须在onLoad阶段注册（默认行为，保守策略）
     *         false 表示可以延迟到onEnable阶段注册（如EaseCation平台）
     */
    default boolean requiresEarlyRegistration() {
        return true; // 默认保守策略：要求提前注册
    }

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

