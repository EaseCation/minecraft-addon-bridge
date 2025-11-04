package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.block.customblock.CustomBlockDefinition;
import net.easecation.bridge.adapter.pnx.mapper.BlockDefinitionBuilder;
import net.easecation.bridge.core.BlockDef;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储BlockDef和对应的CustomBlockDefinition的holder
 * 用于动态生成的Block类访问
 */
public class BlockDefinitionHolder {

    private static final Map<String, BlockDef> BLOCK_DEFS = new ConcurrentHashMap<>();
    private static final Map<String, CustomBlockDefinition> DEFINITIONS_CACHE = new ConcurrentHashMap<>();
    private static BlockDefinitionBuilder builder;

    public static void initialize(BlockDefinitionBuilder definitionBuilder) {
        builder = definitionBuilder;
    }

    public static void registerBlockDef(String blockId, BlockDef blockDef) {
        BLOCK_DEFS.put(blockId, blockDef);
    }

    public static CustomBlockDefinition getDefinition(String blockId, cn.nukkit.block.customblock.CustomBlock blockInstance) {
        return DEFINITIONS_CACHE.computeIfAbsent(blockId, id -> {
            BlockDef blockDef = BLOCK_DEFS.get(id);
            if (blockDef == null) {
                throw new IllegalStateException("BlockDef not found for: " + id);
            }
            return builder.build(blockDef, blockInstance);
        });
    }

    public static BlockDef getBlockDef(String blockId) {
        return BLOCK_DEFS.get(blockId);
    }
}
