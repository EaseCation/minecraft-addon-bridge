package net.easecation.bridge.adapter.easecation;

import cn.nukkit.block.Blocks;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityFullNames;
import cn.nukkit.item.Items;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.plugin.PluginBase;
import net.easecation.bridge.adapter.easecation.block.BlockComponentsNBT;
import net.easecation.bridge.adapter.easecation.block.BlockDataDriven;
import net.easecation.bridge.adapter.easecation.block.BlockPermutationNBT;
import net.easecation.bridge.adapter.easecation.block.BlockStatesNBT;
import net.easecation.bridge.adapter.easecation.block.BlockTraitsNBT;
import net.easecation.bridge.adapter.easecation.entity.EntityDataDriven;
import net.easecation.bridge.adapter.easecation.item.ItemDataDriven;
import net.easecation.bridge.core.*;
import org.itxtech.synapseapi.multiprotocol.utils.AvailableEntityIdentifiersPalette;
import org.itxtech.synapseapi.multiprotocol.utils.CreativeItemsPalette;

import java.util.ArrayList;
import java.util.List;

/**
 * EaseCation Nukkit adapter for registering custom blocks and entities.
 * Reads BlockDef/EntityDef from core and uses Nukkit API to register them.
 */
public class EasecationRegistry implements AddonRegistry {
    private final BridgeLogger log;
    private static final Capabilities CAPS = new Capabilities(true);
    private final List<String> registeredEntityIds = new ArrayList<>();

    public EasecationRegistry(BridgeLogger log) {
        this.log = log;
    }

    @Override
    public void registerItems(List<ItemDef> items) {
        if (items.isEmpty()) {
            return;
        }

        log.debug("[EaseCation] Starting item registration: " + items.size() + " items");

        int successCount = 0;
        int failureCount = 0;

        for (ItemDef itemDef : items) {
            String itemId = itemDef.id();
            try {
                log.debug("[EaseCation] Processing item: " + itemId);

                // 1. Allocate custom item ID
                int customItemId = cn.nukkit.item.Items.allocateCustomItemId();
                log.debug("[EaseCation]   - Allocated custom item ID: " + customItemId);

                // 2. Register to ItemDataDriven registry
                log.debug("[EaseCation]   - Registering to ItemDataDriven registry...");
                net.easecation.bridge.adapter.easecation.item.ItemDataDriven.registerItemDef(customItemId, itemDef);

                // 3. Build NBT data
                log.debug("[EaseCation]   - Building NBT data...");
                CompoundTag nbt = buildItemNBT(itemDef);

                // 4. Register custom item with Nukkit
                log.debug("[EaseCation]   - Registering custom item to Nukkit...");
                if (itemDef.isLegacy()) {
                    // Legacy item
                    log.debug("[EaseCation]   - Using Legacy item");
                } else {
                    // Component-based item - use 5-parameter version (with NBT)
                    log.debug("[EaseCation]   - Using Component-based item");
                }
                Items.registerCustomItem(
                        itemId,
                        customItemId,
                        ItemDataDriven.class,
                        (meta, count) -> new ItemDataDriven(customItemId, meta, count),
                        nbt
                );

                // 5. Register to creative mode inventory
                log.debug("[EaseCation]   - Registering to creative mode inventory...");
                CreativeItemsPalette.registerCustomItem(cn.nukkit.item.Item.get(customItemId));

                log.debug("[EaseCation] ✓ Successfully registered: " + itemId + " (customItemId=" + customItemId + ")");
                successCount++;

            } catch (Exception e) {
                failureCount++;
                log.error("[EaseCation] ✗ Failed to register item: " + itemId);
                log.error("[EaseCation]   Error type: " + e.getClass().getSimpleName());
                log.error("[EaseCation]   Error message: " + e.getMessage());

                // Log stack trace with more context
                log.error("[EaseCation]   Stack trace:");
                for (StackTraceElement element : e.getStackTrace()) {
                    log.error("[EaseCation]     at " + element.toString());
                    if (element.getClassName().contains("easecation.bridge")) {
                        break; // Only show our own code in the stack trace
                    }
                }

                // Log item definition details for debugging
                if (itemDef.componentComponents() != null) {
                    log.error("[EaseCation]   Item has components: YES");
                } else {
                    log.error("[EaseCation]   Item has components: NO");
                }
            }
        }

        log.info("[EaseCation] Item registration completed - Success: " + successCount + ", Failed: " + failureCount);
    }

    @Override
    public void registerBlocks(List<BlockDef> blocks) {
        if (blocks.isEmpty()) {
            return;
        }

        log.debug("[EaseCation] Starting block registration: " + blocks.size() + " blocks");

        int successCount = 0;
        int failureCount = 0;

        for (BlockDef blockDef : blocks) {
            String blockId = blockDef.id();
            try {
                log.debug("[EaseCation] Processing block: " + blockId);

                // 1. Allocate runtime ID
                int runtimeId = Blocks.allocateCustomBlockId();
                log.debug("[EaseCation]   - Allocated runtime ID: " + runtimeId);

                // 2. Register to BlockDataDriven registry
                log.debug("[EaseCation]   - Registering to BlockDataDriven registry...");
                BlockDataDriven.registerBlockDef(runtimeId, blockDef);

                // 3. Register custom block with Lambda for multi-protocol support
                log.debug("[EaseCation]   - Registering custom block to Nukkit...");
                Blocks.registerCustomBlock(
                        blockId,
                        runtimeId,
                        BlockDataDriven.class,
                        protocolVersion -> {
                            log.trace("[EaseCation]     Building NBT for protocol version: " + protocolVersion);

                            // Build base NBT data
                            CompoundTag nbt = buildBlockNBT(blockDef, runtimeId);

                            // Add components NBT (protocol-version specific)
                            if (blockDef.components() != null) {
                                log.trace("[EaseCation]       - Adding components for protocol: " + protocolVersion);
                                try {
                                    CompoundTag componentsNBT = BlockComponentsNBT.toNBT(blockDef.components(), false);
                                    // Merge all component tags into the main NBT
                                    for (String key : componentsNBT.getTags().keySet()) {
                                        nbt.put(key, componentsNBT.get(key));
                                    }
                                } catch (Exception e) {
                                    log.warning("[EaseCation]       - Failed to convert components: " + e.getMessage());
                                    throw new RuntimeException("Failed to convert block components for " + blockDef.id(), e);
                                }
                            }

                            // Add permutations NBT (protocol-version specific)
                            if (blockDef.permutations() != null && !blockDef.permutations().isEmpty()) {
                                log.trace("[EaseCation]       - Adding permutations for protocol: " + protocolVersion);
                                try {
                                    ListTag<CompoundTag> permutations = BlockPermutationNBT.toNBT(blockDef.permutations());
                                    if (!permutations.isEmpty()) {
                                        nbt.putList("permutations", permutations);
                                    }
                                } catch (Exception e) {
                                    log.warning("[EaseCation]       - Failed to convert permutations: " + e.getMessage());
                                    throw new RuntimeException("Failed to convert block permutations for " + blockDef.id(), e);
                                }
                            }

                            log.trace("[EaseCation]     NBT build completed, size: " + nbt.getTags().size() + " tags");
                            return nbt;
                        }
                );

                log.debug("[EaseCation] ✓ Successfully registered: " + blockId + " (runtimeId=" + runtimeId + ")");
                successCount++;

            } catch (Exception e) {
                failureCount++;
                log.error("[EaseCation] ✗ Failed to register block: " + blockId);
                log.error("[EaseCation]   Error type: " + e.getClass().getSimpleName());
                log.error("[EaseCation]   Error message: " + e.getMessage());

                // Log stack trace with more context
                log.error("[EaseCation]   Stack trace:");
                for (StackTraceElement element : e.getStackTrace()) {
                    log.error("[EaseCation]     at " + element.toString());
                    if (element.getClassName().contains("easecation.bridge")) {
                        break; // Only show our own code in the stack trace
                    }
                }

                // Log block definition details for debugging
                if (blockDef.components() != null) {
                    log.error("[EaseCation]   Block has components: YES");
                } else {
                    log.error("[EaseCation]   Block has components: NO");
                }
                if (blockDef.description() != null) {
                    log.error("[EaseCation]   Block has description: YES");
                    if (blockDef.description().traits() != null) {
                        log.error("[EaseCation]   Block has traits: YES");
                    }
                } else {
                    log.error("[EaseCation]   Block has description: NO");
                }
            }
        }

        // 5. Rebuild block palette and item mappings
        // Note: EaseCation Nukkit may handle this automatically or use different methods
        // BlockSerializer/ItemSerializer may not be available in all Nukkit versions

        log.info("[EaseCation] Block registration completed - Success: " + successCount + ", Failed: " + failureCount);
    }

    @Override
    public void registerEntities(List<EntityDef> entities) {
        if (entities.isEmpty()) {
            return;
        }

        log.debug("[EaseCation] Starting entity registration: " + entities.size() + " entities");

        int successCount = 0;
        int failureCount = 0;

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

                // 3. Store entity ID for network registration in afterAllRegistrations()
                registeredEntityIds.add(identifier);

                log.debug("[EaseCation] Registered entity: " + identifier);
                successCount++;

            } catch (Exception e) {
                log.error("[EaseCation] Failed to register entity: " + entityDef.id() + " - " + e.getMessage());
                e.printStackTrace();
                failureCount++;
            }
        }

        log.info("[EaseCation] Entity registration completed - Success: " + successCount + ", Failed: " + failureCount);
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
    public boolean requiresEarlyRegistration() {
        // EaseCation平台没有closeRegistry限制，可以在onEnable阶段注册
        // 这样更符合插件生命周期的最佳实践
        return false;
    }

    @Override
    public void setupResourcePackPushing(List<DeployedPack> deployedPacks, BridgeConfig config, Object plugin) {
        if (!(plugin instanceof PluginBase pluginBase)) {
            log.warning("[EaseCation] setupResourcePackPushing: plugin is not a PluginBase, skipping");
            return;
        }

        if (deployedPacks.isEmpty()) {
            log.debug("[EaseCation] No resource packs to push, skipping listener registration");
            return;
        }

        ResourcePackListener listener = new ResourcePackListener(deployedPacks, config, log);
        pluginBase.getServer().getPluginManager().registerEvents(listener, pluginBase);
        log.info("[EaseCation] Resource pack listener registered");
        log.debug("[EaseCation]   - Push resource packs: " + config.isPushResourcePacks());
        log.debug("[EaseCation]   - Push behavior packs: " + config.isPushBehaviorPacks());
        log.debug("[EaseCation]   - Force accept: " + config.isForceAccept());
        log.debug("[EaseCation]   - Total deployed packs: " + deployedPacks.size());
    }

    @Override
    public void afterAllRegistrations() {
        log.debug("[EaseCation] Executing post-registration tasks...");

        try {
            // 重建方块调色板（必须）
            // 这会更新运行时方块调色板，让客户端能够识别自定义方块
            cn.nukkit.block.BlockSerializer.rebuildPalette();
            log.debug("[EaseCation] ✓ Block palette rebuilt");

            // 重建物品运行时映射（必须）
            // 这会更新物品ID映射，确保玩家可以获取和放置自定义方块
            cn.nukkit.item.ItemSerializer.rebuildRuntimeMapping();
            log.debug("[EaseCation] ✓ Item runtime mapping rebuilt");

            // 缓存物品组件数据包（必须，用于自定义物品）
            // 这会缓存所有物品组件定义，用于发送给客户端
            org.itxtech.synapseapi.multiprotocol.utils.ItemComponentDefinitions.cachePackets();
            log.debug("[EaseCation] ✓ Item component definitions cached");

            // 注册自定义实体标识符到网络协议层（必须）
            // 这让客户端能够识别和渲染自定义实体
            if (!registeredEntityIds.isEmpty()) {
                log.debug("[EaseCation] Registering " + registeredEntityIds.size() + " entity identifiers to network palette...");
                for (String entityId : registeredEntityIds) {
                    // 将自定义实体映射到原版实体（洞穴蜘蛛）用于网络传输
                    // 客户端会使用行为包中的定义来渲染实体
                    AvailableEntityIdentifiersPalette.registerCustomEntity(entityId, EntityFullNames.CAVE_SPIDER);
                    log.debug("[EaseCation]   - Registered: " + entityId);
                }

                // 缓存实体标识符数据包，用于发送给客户端
                AvailableEntityIdentifiersPalette.cachePackets();
                log.debug("[EaseCation] ✓ Entity identifier packets cached");
            } else {
                log.debug("[EaseCation] No custom entities to register");
            }

            // 重建生物群系网络缓存（可选，如果支持自定义生物群系）
            cn.nukkit.level.biome.Biomes.rebuildNetworkCache();
            log.debug("[EaseCation] ✓ Biome network cache rebuilt");

            log.info("[EaseCation] Post-registration tasks completed");

        } catch (Exception e) {
            log.error("[EaseCation] Failed to execute post-registration tasks");
            log.error("[EaseCation] Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            throw new RuntimeException("Failed to rebuild runtime mappings", e);
        }
    }

    /**
     * Build NBT data for block registration.
     * The NBT contains block definition including menu category, tags, properties, components, etc.
     *
     * @param blockDef The block definition
     * @param runtimeId The runtime ID allocated for this block
     * @return CompoundTag containing the block NBT data
     */
    private CompoundTag buildBlockNBT(BlockDef blockDef, int runtimeId) {
        try {
            CompoundTag nbt = new CompoundTag();

            // Basic identifier
            log.trace("[EaseCation]     Building NBT for: " + blockDef.id());
            nbt.putString("identifier", blockDef.id());

            // Vanilla block data (contains runtimeId for client recognition)
            nbt.putCompound("vanilla_block_data", new CompoundTag()
                .putInt("block_id", runtimeId)
                .putString("material", "dirt")
            );
            log.trace("[EaseCation]       - Added vanilla_block_data with runtimeId: " + runtimeId);

            // Menu category (for creative inventory)
            if (blockDef.description() != null && blockDef.description().menuCategory() != null) {
                log.trace("[EaseCation]       - Adding menu category");
                CompoundTag menuCategory = new CompoundTag();
                var mc = blockDef.description().menuCategory();

                menuCategory.putString("category", mc.category() != null ? mc.category() : "none");
                if (mc.group() != null) {
                    menuCategory.putString("group", mc.group());
                }
                if (mc.isHiddenInCommands() != null) {
                    menuCategory.putBoolean("is_hidden_in_commands", mc.isHiddenInCommands());
                }

                nbt.putCompound("menu_category", menuCategory);
            }

            // Note: Components and Permutations are added in the Lambda function
            // to support protocol-version specific NBT generation

            // Traits - placement_direction, placement_position, connection
            if (blockDef.description() != null && blockDef.description().traits() != null) {
                log.trace("[EaseCation]       - Adding traits");
                try {
                    ListTag<CompoundTag> traitsNBT = BlockTraitsNBT.toNBT(blockDef.description().traits());
                    if (!traitsNBT.isEmpty()) {
                        nbt.putList("traits", traitsNBT);
                        log.trace("[EaseCation]         Added " + traitsNBT.size() + " trait(s)");
                    }
                } catch (Exception e) {
                    log.warning("[EaseCation]       - Failed to convert traits: " + e.getMessage());
                    throw new RuntimeException("Failed to convert block traits for " + blockDef.id(), e);
                }
            }

            // Properties/States - convert states to properties
            // Use description.states() which contains the structured StatesValue from JSON Schema
            var description = blockDef.description();
            if (description != null && description.states() != null && !description.states().isEmpty()) {
                log.trace("[EaseCation]       - Adding states/properties (" + description.states().size() + " states)");
                try {
                    ListTag<CompoundTag> properties = BlockStatesNBT.toPropertiesNBT(description.states());
                    if (!properties.isEmpty()) {
                        nbt.putList("properties", properties);
                        log.trace("[EaseCation]         Converted to " + properties.size() + " properties");
                    }
                } catch (Exception e) {
                    log.warning("[EaseCation]       - Failed to convert states: " + e.getMessage());
                    throw new RuntimeException("Failed to convert block states for " + blockDef.id(), e);
                }
            } else if (blockDef.states() != null && !blockDef.states().isEmpty()) {
                // Fallback to legacy states (Map<String, Object>) for backward compatibility
                log.trace("[EaseCation]       - Adding legacy states/properties (" + blockDef.states().size() + " states)");
                try {
                    ListTag<CompoundTag> properties = BlockStatesNBT.toPropertiesNBTLegacy(blockDef.states());
                    if (!properties.isEmpty()) {
                        nbt.putList("properties", properties);
                        log.trace("[EaseCation]         Converted to " + properties.size() + " properties");
                    }
                } catch (Exception e) {
                    log.warning("[EaseCation]       - Failed to convert legacy states: " + e.getMessage());
                    throw new RuntimeException("Failed to convert block states for " + blockDef.id(), e);
                }
            }

            // Molang version
            nbt.putInt("molangVersion", 0);

            // Client prediction overrides
            nbt.putInt("client_prediction_overrides", 0);

            log.trace("[EaseCation]     NBT build completed for: " + blockDef.id());
            return nbt;

        } catch (Exception e) {
            log.error("[EaseCation]     Failed to build NBT for block: " + blockDef.id());
            log.error("[EaseCation]     Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            throw new RuntimeException("Failed to build NBT for block " + blockDef.id(), e);
        }
    }

    /**
     * Build NBT data for item registration.
     * The NBT contains item definition including properties and components.
     *
     * <p>For Legacy mode (v1.10):
     * <ul>
     *   <li>Simple items: Returns null (no NBT needed)</li>
     *   <li>Food items: Returns NBT with only minecraft:food and minecraft:use_duration</li>
     * </ul>
     *
     * <p>For Component-based mode (v1.19+):
     * <ul>
     *   <li>Returns full NBT with item_properties and all components</li>
     * </ul>
     *
     * @param itemDef The item definition
     * @return CompoundTag containing the item NBT data, or null for Legacy simple items
     */
    private CompoundTag buildItemNBT(ItemDef itemDef) {
        try {
            log.trace("[EaseCation]     Building NBT for: " + itemDef.id());

            // Branch on registration mode
            if (itemDef.isLegacy()) {
                return buildLegacyItemNBT(itemDef);
            } else {
                return buildComponentBasedItemNBT(itemDef);
            }

        } catch (Exception e) {
            log.error("[EaseCation]     Failed to build NBT for item: " + itemDef.id());
            log.error("[EaseCation]     Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            throw new RuntimeException("Failed to build NBT for item " + itemDef.id(), e);
        }
    }

    /**
     * Build NBT for Legacy mode (v1.10) items.
     * Returns null for simple items, food NBT for food items.
     */
    private CompoundTag buildLegacyItemNBT(ItemDef itemDef) {
        log.trace("[EaseCation]       - Legacy mode item");
        CompoundTag nbt = net.easecation.bridge.adapter.easecation.item.ItemComponentsNBT_Legacy.toNBT(itemDef);

        if (nbt == null) {
            log.trace("[EaseCation]       - No NBT (legacy simple item)");
        } else {
            log.trace("[EaseCation]       - Legacy food item NBT built");
        }

        return nbt;
    }

    /**
     * Build NBT for Component-based mode (v1.19+) items.
     * Returns full NBT with item_properties and all components.
     */
    private CompoundTag buildComponentBasedItemNBT(ItemDef itemDef) {
        log.trace("[EaseCation]       - Component-based mode item");

        // Basic identifier
        CompoundTag nbt = new CompoundTag();
        nbt.putString("identifier", itemDef.id());

        // Menu category (for creative inventory) - same as block implementation
        if (itemDef.menuCategory() != null) {
            log.trace("[EaseCation]       - Adding menu category");
            CompoundTag menuCategory = new CompoundTag();
            var mc = itemDef.menuCategory();

            menuCategory.putString("category", mc.category() != null ? mc.category() : "items");
            if (mc.group() != null && !mc.group().isEmpty()) {
                menuCategory.putString("group", mc.group());
            }
            if (mc.isHiddenInCommands() != null) {
                menuCategory.putBoolean("is_hidden_in_commands", mc.isHiddenInCommands());
            }

            nbt.putCompound("menu_category", menuCategory);
        } else {
            // Default: add default menu_category (category="items")
            log.trace("[EaseCation]       - Adding default menu category");
            nbt.putCompound("menu_category", new CompoundTag()
                .putString("category", "items")
                .putString("group", "")
                .putBoolean("is_hidden_in_commands", false)
            );
        }

        // Add components NBT
        if (itemDef.componentComponents() != null) {
            log.trace("[EaseCation]       - Adding components");
            try {
                CompoundTag componentsNBT = net.easecation.bridge.adapter.easecation.item.ItemComponentsNBT.toNBT(
                    itemDef,  // Pass the full ItemDef (includes menu_category)
                    itemDef.id()
                );
                // Merge all component tags into the main NBT
                for (String key : componentsNBT.getTags().keySet()) {
                    nbt.put(key, componentsNBT.get(key));
                }
            } catch (Exception e) {
                log.warning("[EaseCation]       - Failed to convert components: " + e.getMessage());
                throw new RuntimeException("Failed to convert item components for " + itemDef.id(), e);
            }
        }

        log.trace("[EaseCation]     NBT build completed for: " + itemDef.id());
        return nbt;
    }
}


