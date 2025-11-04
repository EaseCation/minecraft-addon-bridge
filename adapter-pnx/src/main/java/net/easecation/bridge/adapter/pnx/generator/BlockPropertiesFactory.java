package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.block.BlockProperties;
import cn.nukkit.block.property.type.BlockPropertyType;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.dto.block.v1_21_60.StatesValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工厂类用于在ByteBuddy静态初始化器中创建BlockProperties
 * 避免ByteBuddy直接处理可变参数构造函数
 */
public class BlockPropertiesFactory {

    /**
     * 临时缓存，用于在生成类期间传递states数据到静态初始化器
     * Key: block identifier
     * Value: states definition
     */
    private static final ConcurrentHashMap<String, Map<String, StatesValue>> STATES_CACHE = new ConcurrentHashMap<>();

    /**
     * 创建一个没有properties的BlockProperties
     *
     * @param identifier Block标识符
     * @return BlockProperties实例
     */
    public static BlockProperties create(String identifier) {
        // 检查是否有缓存的states
        Map<String, StatesValue> states = STATES_CACHE.remove(identifier);
        if (states != null && !states.isEmpty()) {
            BridgeLoggerHolder.getLogger().info("[BlockPropertiesFactory] Creating BlockProperties with "
                + states.size() + " states for: " + identifier);
            BlockPropertyType<?>[] properties = BlockPropertyConverter.convertStates(states);
            return new BlockProperties(identifier, properties);
        }

        // 无states，创建简单BlockProperties
        return new BlockProperties(identifier);
    }

    /**
     * 注册states到缓存，在生成类之前调用
     * 这样在静态初始化器中调用create()时可以获取states
     *
     * @param identifier Block标识符
     * @param states States定义
     */
    public static void registerStates(String identifier, Map<String, StatesValue> states) {
        if (states != null && !states.isEmpty()) {
            STATES_CACHE.put(identifier, states);
            BridgeLoggerHolder.getLogger().debug("[BlockPropertiesFactory] Registered " + states.size()
                + " states for: " + identifier);
        }
    }

    /**
     * 清理缓存中未使用的states（通常不需要调用，因为create会自动移除）
     */
    public static void clearCache() {
        STATES_CACHE.clear();
    }
}
