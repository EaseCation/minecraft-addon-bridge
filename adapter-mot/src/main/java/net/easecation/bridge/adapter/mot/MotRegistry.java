package net.easecation.bridge.adapter.mot;

import cn.nukkit.block.custom.CustomBlockDefinition;
import cn.nukkit.block.custom.CustomBlockManager;
import cn.nukkit.block.custom.container.BlockContainer;
import cn.nukkit.block.custom.container.BlockContainerFactory;
import cn.nukkit.block.custom.properties.BlockProperties;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.custom.EntityDefinition;
import cn.nukkit.entity.custom.EntityManager;
import cn.nukkit.item.Item;
import net.easecation.bridge.adapter.mot.block.*;
import net.easecation.bridge.adapter.mot.entity.DynamicEntityClassGenerator;
import net.easecation.bridge.adapter.mot.entity.EntityDataDriven;
import net.easecation.bridge.adapter.mot.entity.EntityDefinitionBuilder;
import net.easecation.bridge.adapter.mot.item.DynamicItemClassGenerator;
import net.easecation.bridge.adapter.mot.item.ItemDataDriven;
import net.easecation.bridge.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MOT Nukkit adapter for registering custom items and entities.
 * Uses MOT's ItemCustom and CustomEntity APIs for registration.
 */
public class MotRegistry implements AddonRegistry {
    private final BridgeLogger log;
    private static final Capabilities CAPS = new Capabilities(true);
    private final List<String> registeredEntityIds = new ArrayList<>();

    public MotRegistry(BridgeLogger log) {
        this.log = log;
    }

    @Override
    public void registerItems(List<ItemDef> items) {
        if (items.isEmpty()) {
            log.info("[MOT] No items to register");
            return;
        }

        log.debug("[MOT] Starting item registration: " + items.size() + " items");

        int successCount = 0;
        int failureCount = 0;

        for (ItemDef itemDef : items) {
            String itemId = itemDef.id();
            try {
                log.debug("[MOT] Processing item: " + itemId);

                // 1. Register ItemDef to static registry (for getDefinition lookup)
                log.debug("[MOT]   - Registering ItemDef to static registry...");
                ItemDataDriven.registerItemDef(itemId, itemDef);

                // 2. Extract texture name from icon component or use identifier
                String textureName = extractTextureName(itemDef);
                log.info("[MOT]   - Texture name: " + textureName);

                // 3. Dynamically generate a unique class for this item
                log.debug("[MOT]   - Generating dynamic class for item...");
                Class<? extends ItemDataDriven> dynamicClass = DynamicItemClassGenerator.generateItemClass(itemDef, textureName);
                log.debug("[MOT]   - Generated class: " + dynamicClass.getSimpleName());

                // 4. Register the dynamic class with MOT
                log.debug("[MOT]   - Registering custom item to MOT...");
                Item.registerCustomItem(dynamicClass);

                log.info("[MOT] ✓ Successfully registered item: " + itemId + " (class: " + dynamicClass.getSimpleName() + ")");
                successCount++;

            } catch (Exception e) {
                failureCount++;
                log.error("[MOT] ✗ Failed to register item: " + itemId);
                log.error("[MOT]   Error type: " + e.getClass().getSimpleName());
                log.error("[MOT]   Error message: " + e.getMessage());

                // Log stack trace
                log.error("[MOT]   Stack trace:");
                for (StackTraceElement element : e.getStackTrace()) {
                    log.error("[MOT]     at " + element.toString());
                    if (element.getClassName().contains("easecation.bridge")) {
                        break;
                    }
                }

                // Log item definition details
                if (itemDef.components() != null) {
                    log.error("[MOT]   Item has components: YES");
                } else {
                    log.error("[MOT]   Item has components: NO");
                }
            }
        }

        log.info("[MOT] Item registration completed - Success: " + successCount + ", Failed: " + failureCount);
    }

    /**
     * Extract texture name from item definition.
     */
    private String extractTextureName(ItemDef itemDef) {
        if (itemDef.components() != null) {
            Object iconObj = itemDef.components().get("minecraft:icon");
            if (iconObj instanceof String) {
                return (String) iconObj;
            } else if (iconObj instanceof java.util.Map) {
                @SuppressWarnings("unchecked")
                java.util.Map<String, Object> iconMap = (java.util.Map<String, Object>) iconObj;
                Object texturesObj = iconMap.get("textures");
                if (texturesObj instanceof java.util.Map) {
                    @SuppressWarnings("unchecked")
                    java.util.Map<String, String> texturesMap = (java.util.Map<String, String>) texturesObj;
                    String texture = texturesMap.get("default");
                    if (texture != null) {
                        return texture;
                    }
                    if (!texturesMap.isEmpty()) {
                        return texturesMap.values().iterator().next();
                    }
                }
            }
        }

        // Fallback: use identifier as texture name (replace : with _)
        return itemDef.id().replace(":", "_");
    }

    @Override
    public void registerBlocks(List<BlockDef> blocks) {
        if (blocks.isEmpty()) {
            log.info("[MOT] No blocks to register");
            return;
        }

        log.debug("[MOT] Starting block registration: " + blocks.size() + " blocks");

        int successCount = 0;
        int failureCount = 0;
        int skippedCount = 0;

        for (BlockDef blockDef : blocks) {
            String blockId = blockDef.id();
            try {
                log.debug("[MOT] Processing block: " + blockId);

                // TODO: MOT方块state和变体系统存在问题，暂时跳过带states的方块
                // MOT的BlockProperties系统与Bedrock的states映射存在兼容性问题
                // 需要深入研究MOT的变体生成机制（variantGenerations）和状态映射
                if (blockDef.description() != null && blockDef.description().states() != null
                    && !blockDef.description().states().isEmpty()) {
                    skippedCount++;
                    log.warning("[MOT] ⚠ Skipping block with states (not yet supported): " + blockId);
                    log.warning("[MOT]   Block has " + blockDef.description().states().size() + " states");
                    log.warning("[MOT]   TODO: Implement proper BlockProperties mapping for MOT");
                    continue;
                }

                // 1. Allocate block ID (>= 10000)
                log.debug("[MOT]   - Allocating block ID...");
                int nukkitId = BlockIdAllocator.allocate(blockId);
                log.debug("[MOT]   - Assigned Nukkit ID: " + nukkitId);

                // 2. Register BlockDef to static registry
                log.debug("[MOT]   - Registering BlockDef to static registry...");
                BlockDataDriven.registerBlockDef(blockId, blockDef);

                // 3. BlockProperties - currently skipped (see TODO above)
                BlockProperties properties = null;

                // 4. Dynamically generate a unique class for this block
                log.debug("[MOT]   - Generating dynamic class for block...");
                Class<? extends BlockDataDriven> dynamicClass =
                    DynamicBlockClassGenerator.generateBlockClass(blockDef, nukkitId, properties);
                log.debug("[MOT]   - Generated class: " + dynamicClass.getSimpleName());

                // 5. Create a sample block instance for building definition
                BlockContainer sampleBlock;
                if (properties != null) {
                    sampleBlock = new BlockDataDriven.WithProperties(blockId, nukkitId, properties);
                } else {
                    sampleBlock = new BlockDataDriven(blockId, nukkitId) {};
                }

                // 6. Build CustomBlockDefinition
                log.debug("[MOT]   - Building CustomBlockDefinition...");
                CustomBlockDefinition definition =
                    BlockDefinitionBuilder.build(sampleBlock, blockDef, properties);

                // 7. Register definition to cache
                BlockDataDriven.registerDefinition(blockId, definition);

                // 8. Create factory for block instantiation
                final Class<? extends BlockDataDriven> finalDynamicClass = dynamicClass;
                final BlockProperties finalProperties = properties;
                final String finalBlockId = blockId;

                BlockContainerFactory factory = meta -> {
                    try {
                        // For blocks with properties, use constructor that accepts meta parameter
                        // This ensures meta is set correctly from the start, preventing ID errors
                        if (finalProperties != null) {
                            // The dynamically generated class has a constructor(int meta)
                            return finalDynamicClass
                                .getDeclaredConstructor(int.class)
                                .newInstance(meta);
                        } else {
                            // For simple blocks without properties, use no-arg constructor
                            return finalDynamicClass
                                .getDeclaredConstructor()
                                .newInstance();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to create block instance: " + finalBlockId, e);
                    }
                };

                // 9. Register with MOT's CustomBlockManager
                log.debug("[MOT]   - Registering with CustomBlockManager...");
                CustomBlockManager blockManager = CustomBlockManager.get();

                if (properties != null) {
                    // Block with properties
                    blockManager.registerCustomBlock(blockId, nukkitId, properties, definition, factory);
                } else {
                    // Simple block
                    blockManager.registerCustomBlock(blockId, nukkitId, definition, () -> factory.create(0));
                }

                log.info("[MOT] ✓ Successfully registered block: " + blockId +
                    " (class: " + dynamicClass.getSimpleName() + ", ID: " + nukkitId + ")");
                successCount++;

            } catch (Exception e) {
                failureCount++;
                log.error("[MOT] ✗ Failed to register block: " + blockId);
                log.error("[MOT]   Error type: " + e.getClass().getSimpleName());
                log.error("[MOT]   Error message: " + e.getMessage());

                // Log stack trace
                log.error("[MOT]   Stack trace:");
                for (StackTraceElement element : e.getStackTrace()) {
                    log.error("[MOT]     at " + element.toString());
                    if (element.getClassName().contains("easecation.bridge")) {
                        break;
                    }
                }

                // Log block definition details
                if (blockDef.components() != null) {
                    log.error("[MOT]   Block has components: YES");
                } else {
                    log.error("[MOT]   Block has components: NO");
                }
                if (blockDef.description() != null && blockDef.description().states() != null) {
                    log.error("[MOT]   Block has states: YES (" +
                        blockDef.description().states().size() + " states)");
                } else {
                    log.error("[MOT]   Block has states: NO");
                }
            }
        }

        log.info("[MOT] Block registration completed - Success: " + successCount +
            ", Failed: " + failureCount + ", Skipped: " + skippedCount);
        if (skippedCount > 0) {
            log.warning("[MOT] " + skippedCount + " block(s) were skipped due to unsupported states/variants");
            log.warning("[MOT] TODO: Implement BlockProperties support for blocks with states");
        }
    }

    @Override
    public void registerEntities(List<EntityDef> entities) {
        if (entities.isEmpty()) {
            log.info("[MOT] No entities to register");
            return;
        }

        log.debug("[MOT] Starting entity registration: " + entities.size() + " entities");

        int successCount = 0;
        int failureCount = 0;

        for (EntityDef entityDef : entities) {
            String identifier = entityDef.id();

            // Validate identifier
            if (identifier == null || identifier.isEmpty() || identifier.equals("unknown:entity")) {
                log.error("[MOT] ✗ Entity has invalid identifier, skipping: " + identifier);
                failureCount++;
                continue;
            }
            try {
                log.debug("[MOT] Processing entity: " + identifier);

                // 1. Register EntityDef to static registry
                log.debug("[MOT]   - Registering EntityDef to static registry...");
                EntityDataDriven.registerEntityDef(identifier, entityDef);

                // 2. Dynamically generate a unique class for this entity
                log.debug("[MOT]   - Generating dynamic class for entity...");
                Class<? extends EntityDataDriven> dynamicClass = DynamicEntityClassGenerator.generateEntityClass(entityDef);
                log.debug("[MOT]   - Generated class: " + dynamicClass.getSimpleName());

                // 3. Build EntityDefinition using the dynamic class
                log.debug("[MOT]   - Building EntityDefinition...");
                EntityDefinition entityDefinition = EntityDefinitionBuilder.build(entityDef, dynamicClass);

                // 4. Register EntityDefinition to cache
                EntityDataDriven.registerDefinition(identifier, entityDefinition);

                // 5. Check if experiment mode is enabled (required for custom entities)
                cn.nukkit.Server server = cn.nukkit.Server.getInstance();
                if (server != null && !server.enableExperimentMode) {
                    log.error("[MOT] ✗ Cannot register entity '" + identifier + "': Experiment mode is not enabled!");
                    log.error("[MOT]   Please set 'enable-experiment-mode=true' in server.properties");
                    failureCount++;
                    continue;
                }

                // 6. Register with MOT's EntityManager
                log.debug("[MOT]   - Registering with EntityManager...");
                try {
                    EntityManager.get().registerDefinition(entityDefinition);
                    log.debug("[MOT]   - EntityManager registration completed");
                } catch (Exception e) {
                    log.error("[MOT]   - EntityManager registration failed: " + e.getMessage());
                    throw e;
                }

                // Track registered entities
                registeredEntityIds.add(identifier);

                log.info("[MOT] ✓ Successfully registered entity: " + identifier + " (class: " + dynamicClass.getSimpleName() + ")");
                successCount++;

            } catch (Exception e) {
                failureCount++;
                log.error("[MOT] ✗ Failed to register entity: " + identifier);
                log.error("[MOT]   Error type: " + e.getClass().getSimpleName());
                log.error("[MOT]   Error message: " + e.getMessage());

                // Log stack trace
                log.error("[MOT]   Stack trace:");
                for (StackTraceElement element : e.getStackTrace()) {
                    log.error("[MOT]     at " + element.toString());
                    if (element.getClassName().contains("easecation.bridge")) {
                        break;
                    }
                }
            }
        }

        log.info("[MOT] Entity registration completed - Success: " + successCount + ", Failed: " + failureCount);
    }

    @Override
    public void registerRecipes(List<RecipeDef> recipes) {
        log.info("[MOT] registerRecipes size=" + recipes.size());
        // TODO: Implement recipe registration
    }

    @Override
    public Capabilities capabilities() {
        return CAPS;
    }

    @Override
    public void setupResourcePackPushing(List<DeployedPack> deployedPacks, BridgeConfig config, Object plugin) {
        if (deployedPacks == null || deployedPacks.isEmpty()) {
            log.info("[MOT] No deployed packs to register");
            return;
        }

        log.info("[MOT] Setting up resource pack pushing for " + deployedPacks.size() + " pack(s)");

        try {
            // Get Server's ResourcePackManager
            cn.nukkit.Server server = cn.nukkit.Server.getInstance();
            if (server == null) {
                log.error("[MOT] Cannot get Server instance for resource pack registration");
                return;
            }

            cn.nukkit.resourcepacks.ResourcePackManager packManager = server.getResourcePackManager();
            if (packManager == null) {
                log.error("[MOT] ResourcePackManager is null");
                return;
            }

            // Create and register our custom loader
            PluginResourcePackLoader loader = new PluginResourcePackLoader(deployedPacks, config, log);
            packManager.registerPackLoader(loader);

            // Reload packs to apply changes
            log.info("[MOT] Reloading resource packs...");
            packManager.reloadPacks();

            log.info("[MOT] ✓ Resource pack pushing setup completed");

        } catch (Exception e) {
            log.error("[MOT] ✗ Failed to setup resource pack pushing");
            log.error("[MOT]   Error type: " + e.getClass().getSimpleName());
            log.error("[MOT]   Error message: " + e.getMessage());

            // Log stack trace
            log.error("[MOT]   Stack trace:");
            for (StackTraceElement element : e.getStackTrace()) {
                log.error("[MOT]     at " + element.toString());
                if (element.getClassName().contains("easecation.bridge")) {
                    break;
                }
            }
        }
    }

    @Override
    public void afterAllRegistrations() {
        // MOT may need to rebuild mappings similar to EaseCation
        // Check if MOT has ItemSerializer.rebuildRuntimeMapping() or similar
        log.info("[MOT] Registration completed");

        // TODO: Call MOT-specific post-processing if needed
        // Example: cn.nukkit.item.ItemSerializer.rebuildRuntimeMapping();
    }
}

