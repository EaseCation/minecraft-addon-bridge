package net.easecation.bridge.adapter.pm1e;

import cn.nukkit.block.custom.CustomBlockManager;
import cn.nukkit.entity.custom.EntityDefinition;
import cn.nukkit.entity.custom.EntityManager;
import cn.nukkit.item.custom.CustomItemManager;
import cn.nukkit.item.custom.ItemDefinition;
import net.easecation.bridge.adapter.pm1e.block.BlockDataDriven;
import net.easecation.bridge.adapter.pm1e.block.BlockIdAllocator;
import net.easecation.bridge.adapter.pm1e.block.DynamicBlockClassGenerator;
import net.easecation.bridge.adapter.pm1e.entity.DynamicEntityClassGenerator;
import net.easecation.bridge.adapter.pm1e.entity.EntityDataDriven;
import net.easecation.bridge.adapter.pm1e.entity.EntityDefinitionBuilder;
import net.easecation.bridge.adapter.pm1e.item.DynamicItemClassGenerator;
import net.easecation.bridge.adapter.pm1e.item.ItemDataDriven;
import net.easecation.bridge.adapter.pm1e.item.ItemDefinitionBuilder;
import net.easecation.bridge.adapter.pm1e.item.ItemIdAllocator;
import net.easecation.bridge.core.*;

import java.util.List;

/**
 * PM1E Nukkit adapter for registering custom items, blocks, and entities.
 * Uses PM1E's CustomItemManager, CustomBlockManager, and EntityManager APIs.
 */
public class Pm1eRegistry implements AddonRegistry {
    private final BridgeLogger log;
    private static final Capabilities CAPS = new Capabilities(true);

    private final ItemIdAllocator itemIdAllocator = new ItemIdAllocator();
    private final BlockIdAllocator blockIdAllocator = new BlockIdAllocator();

    public Pm1eRegistry(BridgeLogger log) {
        this.log = log;
    }

    @Override
    public void registerItems(List<ItemDef> items) {
        if (items.isEmpty()) {
            log.info("[PM1E] No items to register");
            return;
        }

        log.debug("[PM1E] Starting item registration: " + items.size() + " items");

        int successCount = 0;
        int failureCount = 0;

        for (ItemDef itemDef : items) {
            String itemId = itemDef.id();
            try {
                log.debug("[PM1E] Processing item: " + itemId);

                int nukkitId = itemIdAllocator.allocate(itemId);
                log.debug("[PM1E]   - Allocated ID: " + nukkitId);

                ItemDataDriven.registerItemDef(itemId, itemDef);

                String textureName = extractTextureName(itemDef);
                if (textureName != null) {
                    log.debug("[PM1E]   - Texture name: " + textureName);
                }

                Class<? extends ItemDataDriven> dynamicClass = DynamicItemClassGenerator.generateItemClass(itemDef, nukkitId);
                log.debug("[PM1E]   - Generated class: " + dynamicClass.getSimpleName());

                ItemDefinition definition = ItemDefinitionBuilder.build(dynamicClass, itemDef, nukkitId, textureName);
                ItemDataDriven.registerDefinition(itemId, definition);

                CustomItemManager.get().registerDefinition(definition);

                log.info("[PM1E] ✓ Successfully registered item: " + itemId + " (ID: " + nukkitId + ")");
                successCount++;

            } catch (Exception e) {
                failureCount++;
                log.error("[PM1E] ✗ Failed to register item: " + itemId);
                log.error("[PM1E]   Error: " + e.getMessage());
                if (log instanceof NukkitLoggerAdapter) {
                    e.printStackTrace();
                }
            }
        }

        log.info("[PM1E] Item registration completed - Success: " + successCount + ", Failed: " + failureCount);
    }

    @Override
    public void registerBlocks(List<BlockDef> blocks) {
        if (blocks.isEmpty()) {
            log.info("[PM1E] No blocks to register");
            return;
        }

        log.debug("[PM1E] Starting block registration: " + blocks.size() + " blocks");

        int successCount = 0;
        int failureCount = 0;

        for (BlockDef blockDef : blocks) {
            String blockId = blockDef.id();
            try {
                log.debug("[PM1E] Processing block: " + blockId);

                int nukkitId = blockIdAllocator.allocate(blockId);
                log.debug("[PM1E]   - Allocated ID: " + nukkitId);

                BlockDataDriven.registerBlockDef(blockId, blockDef);

                Class<? extends BlockDataDriven> dynamicClass = DynamicBlockClassGenerator.generateBlockClass(blockDef, nukkitId);
                log.debug("[PM1E]   - Generated class: " + dynamicClass.getSimpleName());

                CustomBlockManager.get().registerCustomBlock(blockId, nukkitId, () -> {
                    try {
                        return dynamicClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to create block instance: " + blockId, e);
                    }
                });

                log.info("[PM1E] ✓ Successfully registered block: " + blockId + " (ID: " + nukkitId + ")");
                successCount++;

            } catch (Exception e) {
                failureCount++;
                log.error("[PM1E] ✗ Failed to register block: " + blockId);
                log.error("[PM1E]   Error: " + e.getMessage());
                if (log instanceof NukkitLoggerAdapter) {
                    e.printStackTrace();
                }
            }
        }

        log.info("[PM1E] Block registration completed - Success: " + successCount + ", Failed: " + failureCount);
    }

    @Override
    public void registerEntities(List<EntityDef> entities) {
        if (entities.isEmpty()) {
            log.info("[PM1E] No entities to register");
            return;
        }

        log.debug("[PM1E] Starting entity registration: " + entities.size() + " entities");

        int successCount = 0;
        int failureCount = 0;

        for (EntityDef entityDef : entities) {
            String entityId = entityDef.id();
            try {
                log.debug("[PM1E] Processing entity: " + entityId);

                EntityDataDriven.registerEntityDef(entityId, entityDef);

                Class<? extends EntityDataDriven> dynamicClass = DynamicEntityClassGenerator.generateEntityClass(entityDef);
                log.debug("[PM1E]   - Generated class: " + dynamicClass.getSimpleName());

                EntityDefinition definition = EntityDefinitionBuilder.build(dynamicClass, entityDef);
                EntityDataDriven.registerDefinition(entityId, definition);

                EntityManager.get().registerDefinition(definition);

                log.info("[PM1E] ✓ Successfully registered entity: " + entityId);
                successCount++;

            } catch (Exception e) {
                failureCount++;
                log.error("[PM1E] ✗ Failed to register entity: " + entityId);
                log.error("[PM1E]   Error: " + e.getMessage());
                if (log instanceof NukkitLoggerAdapter) {
                    e.printStackTrace();
                }
            }
        }

        log.info("[PM1E] Entity registration completed - Success: " + successCount + ", Failed: " + failureCount);
    }

    @Override
    public void registerRecipes(List<RecipeDef> recipes) {
        log.info("[PM1E] Recipe registration not yet implemented - skipping " + recipes.size() + " recipes");
    }

    @Override
    public Capabilities capabilities() {
        return CAPS;
    }

    @Override
    public void afterAllRegistrations() {
        // PM1E Server会在enablePlugins(STARTUP)后自动调用closeRegistry()
        // 参考：Server.java:540-541
        // CustomItemManager.get().closeRegistry();
        // EntityManager.get().closeRegistry();
        //
        // 插件不应该手动调用closeRegistry，否则会导致：
        // java.lang.IllegalStateException: Item registry was already closed
        //
        // 这符合PM1E官方设计和示例代码的实践：
        // - reference/nk-custom-samples 中所有示例都不调用closeRegistry
        // - PNX和MOT适配器也不调用closeRegistry

        log.info("[PM1E] Registration completed");
        log.info("[PM1E] Item and entity registries will be closed automatically by PM1E Server");
    }

    @Override
    public void setupResourcePackPushing(List<DeployedPack> deployedPacks, BridgeConfig config, Object plugin) {
        if (deployedPacks == null || deployedPacks.isEmpty()) {
            log.info("[PM1E] No resource packs to load");
            return;
        }

        try {
            // Create and register resource pack loader
            Pm1eResourcePackLoader loader = new Pm1eResourcePackLoader(deployedPacks, config, log);

            // Get ResourcePackManager from server
            cn.nukkit.Server server = cn.nukkit.Server.getInstance();
            cn.nukkit.resourcepacks.ResourcePackManager packManager = server.getResourcePackManager();

            // Register the loader
            packManager.registerPackLoader(loader);

            // Reload packs to trigger loading
            packManager.reloadPacks();

            log.info("[PM1E] Resource pack loader registered successfully");

        } catch (Exception e) {
            log.error("[PM1E] Failed to setup resource pack pushing: " + e.getMessage());
            log.error("[PM1E] Stack trace:");
            for (StackTraceElement element : e.getStackTrace()) {
                log.error("[PM1E]   at " + element.toString());
                if (element.getClassName().contains("easecation.bridge")) {
                    break;
                }
            }
        }
    }

    private String extractTextureName(ItemDef itemDef) {
        if (itemDef.isLegacy()) {
            return null;
        }

        if (itemDef.componentComponents() != null
            && itemDef.componentComponents().minecraft_icon() != null) {
            net.easecation.bridge.core.dto.item.v1_21_60.Icon icon =
                itemDef.componentComponents().minecraft_icon();

            if (icon instanceof net.easecation.bridge.core.dto.item.v1_21_60.Icon.Icon_Variant0 variant0) {
                return variant0.value();
            } else if (icon instanceof net.easecation.bridge.core.dto.item.v1_21_60.Icon.Icon_Variant1 variant1) {
                if (variant1.textures() != null && variant1.textures().defaultField() != null) {
                    return variant1.textures().defaultField();
                }
            }
        }

        return itemDef.id().replace(":", "_");
    }
}

