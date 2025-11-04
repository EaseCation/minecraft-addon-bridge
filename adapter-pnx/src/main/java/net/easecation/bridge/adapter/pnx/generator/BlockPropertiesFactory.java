package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.block.BlockProperties;

/**
 * 工厂类用于在ByteBuddy静态初始化器中创建BlockProperties
 * 避免ByteBuddy直接处理可变参数构造函数
 */
public class BlockPropertiesFactory {

    /**
     * 创建一个没有properties的BlockProperties
     *
     * @param identifier Block标识符
     * @return BlockProperties实例
     */
    public static BlockProperties create(String identifier) {
        return new BlockProperties(identifier);
    }
}
