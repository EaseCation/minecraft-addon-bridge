package net.easecation.bridge.adapter.mot.block;

import cn.nukkit.block.custom.CustomBlockDefinition;
import cn.nukkit.block.custom.container.BlockContainer;
import cn.nukkit.block.custom.container.BlockStorageContainer;
import cn.nukkit.block.custom.container.CustomBlock;
import cn.nukkit.block.custom.container.CustomBlockMeta;
import cn.nukkit.block.custom.properties.BlockProperties;
import net.easecation.bridge.core.BlockDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data-driven custom block implementation for MOT Nukkit.
 * Loads block properties dynamically from BlockDef based on Bedrock behavior pack format.
 *
 * <p>MOT uses CustomBlock/CustomBlockMeta to implement custom blocks.
 * This class maintains a static registry to support multiple blocks with a single class.</p>
 */
public abstract class BlockDataDriven extends CustomBlock implements BlockContainer {

    // Static registry to map identifier to BlockDef
    private static final Map<String, BlockDef> BLOCK_DEF_REGISTRY = new ConcurrentHashMap<>();

    // Static registry to map identifier to CustomBlockDefinition
    private static final Map<String, CustomBlockDefinition> DEFINITION_CACHE = new ConcurrentHashMap<>();

    // Static registry to map identifier to BlockProperties
    private static final Map<String, BlockProperties> PROPERTIES_REGISTRY = new ConcurrentHashMap<>();

    protected final String identifier;

    /**
     * Constructor for MOT CustomBlock.
     * @param identifier The block identifier (e.g., "namespace:block_name")
     * @param nukkitId The Nukkit block ID (>= 10000)
     */
    public BlockDataDriven(String identifier, int nukkitId) {
        super(identifier, nukkitId);
        this.identifier = identifier;
    }

    /**
     * Register a BlockDef for a given identifier.
     */
    public static void registerBlockDef(String identifier, BlockDef blockDef) {
        BLOCK_DEF_REGISTRY.put(identifier, blockDef);
    }

    /**
     * Register a pre-built CustomBlockDefinition for a given identifier.
     */
    public static void registerDefinition(String identifier, CustomBlockDefinition definition) {
        DEFINITION_CACHE.put(identifier, definition);
    }

    /**
     * Register BlockProperties for a given identifier.
     */
    public static void registerProperties(String identifier, BlockProperties properties) {
        PROPERTIES_REGISTRY.put(identifier, properties);
    }

    /**
     * Get the BlockDef for a given identifier.
     */
    public static BlockDef getBlockDef(String identifier) {
        return BLOCK_DEF_REGISTRY.get(identifier);
    }

    /**
     * Get the CustomBlockDefinition for a given identifier.
     */
    public static CustomBlockDefinition getDefinition(String identifier) {
        return DEFINITION_CACHE.get(identifier);
    }

    /**
     * Get the BlockProperties for a given identifier.
     */
    public static BlockProperties getProperties(String identifier) {
        return PROPERTIES_REGISTRY.get(identifier);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Data-driven block with BlockProperties support.
     *
     * TODO: 由于MOT的BlockProperties系统存在兼容性问题，此类暂时不使用
     * 目前仅注册简单方块（无states），待MOT的states映射问题解决后启用此功能
     */
    public static class WithProperties extends CustomBlockMeta implements BlockStorageContainer {
        protected final String identifier;

        public WithProperties(String identifier, int nukkitId, BlockProperties properties) {
            super(identifier, nukkitId, properties);
            this.identifier = identifier;
        }

        /**
         * Constructor with explicit meta value for proper initialization.
         * This ensures the block state (meta) is set correctly from the start.
         */
        public WithProperties(String identifier, int nukkitId, BlockProperties properties, int meta) {
            super(identifier, nukkitId, properties, meta);
            this.identifier = identifier;
        }

        @Override
        public String getIdentifier() {
            return identifier;
        }

        public BlockDef getBlockDef() {
            return BLOCK_DEF_REGISTRY.get(identifier);
        }

        public CustomBlockDefinition getCustomDefinition() {
            return DEFINITION_CACHE.get(identifier);
        }
    }
}
