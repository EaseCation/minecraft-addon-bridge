package net.easecation.bridge.adapter.easecation;

import cn.nukkit.block.Blocks;
import cn.nukkit.entity.Entity;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.plugin.PluginBase;
import net.easecation.bridge.adapter.easecation.block.BlockComponentsNBT;
import net.easecation.bridge.adapter.easecation.block.BlockDataDriven;
import net.easecation.bridge.adapter.easecation.block.BlockPermutationNBT;
import net.easecation.bridge.adapter.easecation.block.BlockStatesNBT;
import net.easecation.bridge.adapter.easecation.block.BlockTraitsNBT;
import net.easecation.bridge.adapter.easecation.entity.EntityDataDriven;
import net.easecation.bridge.core.*;

import java.util.List;

/**
 * EaseCation Nukkit adapter for registering custom blocks and entities.
 * Reads BlockDef/EntityDef from core and uses Nukkit API to register them.
 */
public class EasecationRegistry implements AddonRegistry {
    private final BridgeLogger log;
    private static final Capabilities CAPS = new Capabilities(true);

    public EasecationRegistry(BridgeLogger log) {
        this.log = log;
    }

    @Override
    public void registerItems(List<ItemDef> items) {
        log.info("[EaseCation] registerItems size=" + items.size());
        // TODO: Implement item registration
    }

    @Override
    public void registerBlocks(List<BlockDef> blocks) {
        log.info("[EaseCation] ========================================");
        log.info("[EaseCation] Starting block registration");
        log.info("[EaseCation] Total blocks to register: " + blocks.size());
        log.info("[EaseCation] ========================================");

        int successCount = 0;
        int failureCount = 0;

        for (BlockDef blockDef : blocks) {
            String blockId = blockDef.id();
            try {
                log.info("[EaseCation] Processing block: " + blockId);

                // 1. Allocate runtime ID
                int runtimeId = Blocks.allocateCustomBlockId();
                log.fine("[EaseCation]   - Allocated runtime ID: " + runtimeId);

                // 2. Build NBT data
                log.fine("[EaseCation]   - Building NBT data...");
                CompoundTag nbt = buildBlockNBT(blockDef);
                log.fine("[EaseCation]   - NBT data built successfully, size: " + nbt.getTags().size() + " tags");

                // 3. Register to BlockDataDriven registry
                log.fine("[EaseCation]   - Registering to BlockDataDriven registry...");
                BlockDataDriven.registerBlockDef(runtimeId, blockDef);

                // 4. Register custom block
                log.fine("[EaseCation]   - Registering custom block to Nukkit...");
                Blocks.registerCustomBlock(
                        blockId,
                        runtimeId,
                        BlockDataDriven.class,
                        nbt
                );

                log.info("[EaseCation] ✓ Successfully registered: " + blockId + " (runtimeId=" + runtimeId + ")");
                successCount++;

            } catch (Exception e) {
                failureCount++;
                log.severe("[EaseCation] ✗ Failed to register block: " + blockId);
                log.severe("[EaseCation]   Error type: " + e.getClass().getSimpleName());
                log.severe("[EaseCation]   Error message: " + e.getMessage());

                // Log stack trace with more context
                log.severe("[EaseCation]   Stack trace:");
                for (StackTraceElement element : e.getStackTrace()) {
                    log.severe("[EaseCation]     at " + element.toString());
                    if (element.getClassName().contains("easecation.bridge")) {
                        break; // Only show our own code in the stack trace
                    }
                }

                // Log block definition details for debugging
                if (blockDef.components() != null) {
                    log.severe("[EaseCation]   Block has components: YES");
                } else {
                    log.severe("[EaseCation]   Block has components: NO");
                }
                if (blockDef.description() != null) {
                    log.severe("[EaseCation]   Block has description: YES");
                    if (blockDef.description().traits() != null) {
                        log.severe("[EaseCation]   Block has traits: YES");
                    }
                } else {
                    log.severe("[EaseCation]   Block has description: NO");
                }
            }
        }

        // 5. Rebuild block palette and item mappings
        // Note: EaseCation Nukkit may handle this automatically or use different methods
        // BlockSerializer/ItemSerializer may not be available in all Nukkit versions

        log.info("[EaseCation] ========================================");
        log.info("[EaseCation] Block registration completed");
        log.info("[EaseCation] Success: " + successCount + " blocks");
        log.info("[EaseCation] Failed: " + failureCount + " blocks");
        log.info("[EaseCation] ========================================");
    }

    @Override
    public void registerEntities(List<EntityDef> entities) {
        log.info("[EaseCation] registerEntities: Starting registration of " + entities.size() + " entities");

        for (EntityDef entityDef : entities) {
            try {
                String identifier = entityDef.id();

                // 1. Register components to EntityDataDriven registry
                EntityDataDriven.registerComponents(identifier, entityDef.components());

                // 2. Register entity factory with Nukkit (using BiFunction)
                Entity.registerEntity(
                        identifier,
                        identifier,
                        EntityDataDriven.class,
                        (chunk, nbt) -> {
                            // Set identifier in NBT so EntityDataDriven can load it
                            if (!nbt.contains("identifier")) {
                                nbt.putString("identifier", identifier);
                            }
                            return new EntityDataDriven(chunk, nbt);
                        }
                );

                log.info("[EaseCation] Registered entity: " + identifier);

            } catch (Exception e) {
                log.severe("[EaseCation] Failed to register entity: " + entityDef.id() + " - " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerRecipes(List<RecipeDef> recipes) {
        log.info("[EaseCation] registerRecipes size=" + recipes.size());
        // TODO: Implement recipe registration
    }

    @Override
    public Capabilities capabilities() {
        return CAPS;
    }

    @Override
    public void setupResourcePackPushing(List<DeployedPack> deployedPacks, Object plugin) {
        if (!(plugin instanceof PluginBase pluginBase)) {
            log.warning("[EaseCation] setupResourcePackPushing: plugin is not a PluginBase, skipping");
            return;
        }

        if (deployedPacks.isEmpty()) {
            log.info("[EaseCation] No resource packs to push, skipping listener registration");
            return;
        }

        ResourcePackListener listener = new ResourcePackListener(deployedPacks, log);
        pluginBase.getServer().getPluginManager().registerEvents(listener, pluginBase);
        log.info("[EaseCation] Resource pack listener registered, will push " + deployedPacks.size() + " pack(s) to players");
    }

    @Override
    public void afterAllRegistrations() {
        log.info("[EaseCation] ========================================");
        log.info("[EaseCation] Executing post-registration tasks...");
        log.info("[EaseCation] ========================================");

        try {
            // 重建方块调色板（必须）
            // 这会更新运行时方块调色板，让客户端能够识别自定义方块
            cn.nukkit.block.BlockSerializer.rebuildPalette();
            log.info("[EaseCation] ✓ Block palette rebuilt successfully");

            // 重建物品运行时映射（必须）
            // 这会更新物品ID映射，确保玩家可以获取和放置自定义方块
            cn.nukkit.item.ItemSerializer.rebuildRuntimeMapping();
            log.info("[EaseCation] ✓ Item runtime mapping rebuilt successfully");

            // 重建生物群系网络缓存（可选，如果支持自定义生物群系）
            cn.nukkit.level.biome.Biomes.rebuildNetworkCache();
            log.info("[EaseCation] ✓ Biome network cache rebuilt successfully");

            log.info("[EaseCation] ========================================");
            log.info("[EaseCation] Post-registration tasks completed");
            log.info("[EaseCation] ========================================");

        } catch (Exception e) {
            log.severe("[EaseCation] ========================================");
            log.severe("[EaseCation] Failed to execute post-registration tasks");
            log.severe("[EaseCation] Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            log.severe("[EaseCation] ========================================");
            throw new RuntimeException("Failed to rebuild runtime mappings", e);
        }
    }

    /**
     * Build NBT data for block registration.
     * The NBT contains block definition including menu category, tags, properties, components, etc.
     */
    private CompoundTag buildBlockNBT(BlockDef blockDef) {
        try {
            CompoundTag nbt = new CompoundTag();

            // Basic identifier
            log.finest("[EaseCation]     Building NBT for: " + blockDef.id());
            nbt.putString("identifier", blockDef.id());

            // Menu category (for creative inventory)
            if (blockDef.description() != null && blockDef.description().menuCategory() != null) {
                log.finest("[EaseCation]       - Adding menu category");
                CompoundTag menuCategory = new CompoundTag();
                var mc = blockDef.description().menuCategory();

                menuCategory.putString("category", mc.category() != null ? mc.category().toString() : "none");
                if (mc.group() != null) {
                    menuCategory.putString("group", mc.group());
                }
                if (mc.isHiddenInCommands() != null) {
                    menuCategory.putBoolean("is_hidden_in_commands", mc.isHiddenInCommands());
                }

                nbt.putCompound("menu_category", menuCategory);
            }

            // Components - serialize to NBT using BlockComponentsNBT
            if (blockDef.components() != null) {
                log.finest("[EaseCation]       - Adding components");
                try {
                    CompoundTag componentsNBT = BlockComponentsNBT.toNBT(blockDef.components(), false);
                    // Merge all component tags into the main NBT
                    int componentCount = 0;
                    for (String key : componentsNBT.getTags().keySet()) {
                        nbt.put(key, componentsNBT.get(key));
                        componentCount++;
                    }
                    log.finest("[EaseCation]         Added " + componentCount + " component tags");
                } catch (Exception e) {
                    log.warning("[EaseCation]       - Failed to convert components: " + e.getMessage());
                    throw new RuntimeException("Failed to convert block components for " + blockDef.id(), e);
                }
            }

            // Traits - placement_direction, placement_position, connection
            if (blockDef.description() != null && blockDef.description().traits() != null) {
                log.finest("[EaseCation]       - Adding traits");
                try {
                    ListTag<CompoundTag> traitsNBT = BlockTraitsNBT.toNBT(blockDef.description().traits());
                    if (!traitsNBT.isEmpty()) {
                        nbt.putList("traits", traitsNBT);
                        log.finest("[EaseCation]         Added " + traitsNBT.size() + " trait(s)");
                    }
                } catch (Exception e) {
                    log.warning("[EaseCation]       - Failed to convert traits: " + e.getMessage());
                    throw new RuntimeException("Failed to convert block traits for " + blockDef.id(), e);
                }
            }

            // Properties/States - convert states to properties
            // Note: Using blockDef.states() instead of description.states() because the DTO States record is empty
            if (blockDef.states() != null && !blockDef.states().isEmpty()) {
                log.finest("[EaseCation]       - Adding states/properties (" + blockDef.states().size() + " states)");
                try {
                    ListTag<CompoundTag> properties = BlockStatesNBT.toPropertiesNBT(blockDef.states());
                    if (!properties.isEmpty()) {
                        nbt.putList("properties", properties);
                        log.finest("[EaseCation]         Converted to " + properties.size() + " properties");
                    }
                } catch (Exception e) {
                    log.warning("[EaseCation]       - Failed to convert states: " + e.getMessage());
                    throw new RuntimeException("Failed to convert block states for " + blockDef.id(), e);
                }
            }

            // Permutations - conditional component changes
            if (blockDef.permutations() != null && !blockDef.permutations().isEmpty()) {
                log.finest("[EaseCation]       - Adding permutations (" + blockDef.permutations().size() + " permutations)");
                try {
                    ListTag<CompoundTag> permutations = BlockPermutationNBT.toNBT(blockDef.permutations());
                    if (!permutations.isEmpty()) {
                        nbt.putList("permutations", permutations);
                        log.finest("[EaseCation]         Converted " + permutations.size() + " permutation(s)");
                    }
                } catch (Exception e) {
                    log.warning("[EaseCation]       - Failed to convert permutations: " + e.getMessage());
                    throw new RuntimeException("Failed to convert block permutations for " + blockDef.id(), e);
                }
            }

            // Molang version
            nbt.putInt("molangVersion", 0);

            // Client prediction overrides
            nbt.putInt("client_prediction_overrides", 0);

            log.finest("[EaseCation]     NBT build completed for: " + blockDef.id());
            return nbt;

        } catch (Exception e) {
            log.severe("[EaseCation]     Failed to build NBT for block: " + blockDef.id());
            log.severe("[EaseCation]     Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            throw new RuntimeException("Failed to build NBT for block " + blockDef.id(), e);
        }
    }
}


