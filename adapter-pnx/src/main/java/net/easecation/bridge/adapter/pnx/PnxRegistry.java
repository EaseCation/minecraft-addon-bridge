package net.easecation.bridge.adapter.pnx;

import cn.nukkit.block.Block;
import cn.nukkit.item.customitem.CustomItem;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.registry.RegisterException;
import cn.nukkit.registry.Registries;
import net.easecation.bridge.adapter.pnx.generator.SimpleDynamicBlockGenerator;
import net.easecation.bridge.adapter.pnx.mapper.PnxLegacyItemNBTBuilder;
import net.easecation.bridge.core.*;

import java.util.List;

public class PnxRegistry implements AddonRegistry {
    private final BridgeLogger log;
    private static final Capabilities CAPS = new Capabilities(true);

    private SimpleDynamicBlockGenerator blockGenerator;
    private net.easecation.bridge.adapter.pnx.generator.SimpleDynamicItemGenerator itemGenerator;
    private net.easecation.bridge.adapter.pnx.generator.SimpleDynamicEntityGenerator entityGenerator;
    private cn.nukkit.plugin.Plugin plugin;

    public PnxRegistry(BridgeLogger log) {
        this.log = log;
        // Generators will be created in initialize() after plugin is set
    }

    @Override
    public void initialize(Object plugin) {
        if (plugin instanceof cn.nukkit.plugin.Plugin) {
            this.plugin = (cn.nukkit.plugin.Plugin) plugin;
            log.info("[PNX] Registry initialized with plugin: " + this.plugin.getName());

            // Create generators with plugin instance - CRITICAL for ClassLoader compatibility
            this.blockGenerator = new SimpleDynamicBlockGenerator(this.plugin);
            this.itemGenerator = new net.easecation.bridge.adapter.pnx.generator.SimpleDynamicItemGenerator(this.plugin);
            this.entityGenerator = new net.easecation.bridge.adapter.pnx.generator.SimpleDynamicEntityGenerator(this.plugin);
            log.info("[PNX] Generators initialized with plugin ClassLoader");
        } else {
            log.warning("[PNX] Plugin instance is not a Nukkit Plugin: " + plugin.getClass().getName());
        }
    }

    @Override
    public void registerItems(List<ItemDef> items) {
        log.info("[PNX] registerItems size=" + items.size());

        if (plugin == null) {
            log.error("[PNX] Plugin instance not initialized, cannot register items");
            return;
        }

        for (ItemDef itemDef : items) {
            try {
                // 检查是否为Legacy模式
                if (itemDef.registrationMode() == ItemDef.ItemRegistrationMode.LEGACY) {
                    registerLegacyItem(itemDef);
                } else {
                    registerComponentBasedItem(itemDef);
                }

            } catch (Exception e) {
                log.error("[PNX] Failed to register item: " + itemDef.id());
                log.error("[PNX]   Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 注册Component-based模式的物品（使用官方API）
     */
    private void registerComponentBasedItem(ItemDef itemDef) {
        log.info("[PNX] Registering Component-based item: " + itemDef.id());

        // 1. 使用ByteBuddy生成Item类
        Class<? extends cn.nukkit.item.Item> itemClass = itemGenerator.generateItemClass(itemDef);

        // 2. 注册到PNX
        try {
            Registries.ITEM.registerCustomItem(plugin, itemClass);
            log.info("[PNX] Successfully registered Component-based item: " + itemDef.id());
        } catch (RegisterException e) {
            log.error("[PNX] RegisterException for item: " + itemDef.id());
            log.error("[PNX]   Error: " + e.getMessage());
            throw new RuntimeException("Failed to register Component-based item: " + itemDef.id(), e);
        }
    }

    /**
     * 注册Legacy模式的物品（使用反射注册）
     */
    private void registerLegacyItem(ItemDef itemDef) {
        log.info("[PNX] Registering Legacy mode item: " + itemDef.id());

        try {
            // 1. 使用ByteBuddy生成Item类
            Class<? extends cn.nukkit.item.Item> itemClass = itemGenerator.generateItemClass(itemDef);

            // 2. 使用PnxLegacyItemNBTBuilder生成NBT
            CompoundTag nbt = PnxLegacyItemNBTBuilder.buildNBT(itemDef);

            // 3. 使用LegacyItemRegistrar注册（通过反射）
            LegacyItemRegistrar.registerLegacyItem(plugin, itemClass, itemDef, nbt);

            log.info("[PNX] Successfully registered Legacy mode item: " + itemDef.id());

        } catch (Exception e) {
            log.error("[PNX] Failed to register Legacy mode item: " + itemDef.id());
            log.error("[PNX]   Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            throw new RuntimeException("Failed to register Legacy mode item: " + itemDef.id(), e);
        }
    }

    @Override
    public void registerBlocks(List<BlockDef> blocks) {
        log.info("[PNX] registerBlocks size=" + blocks.size());

        if (plugin == null) {
            log.error("[PNX] Plugin instance not initialized, cannot register blocks");
            return;
        }

        for (BlockDef blockDef : blocks) {
            try {
                log.info("[PNX] Registering block: " + blockDef.id());

                // 1. 使用ByteBuddy生成Block类
                Class<? extends Block> blockClass = blockGenerator.generateBlockClass(blockDef);

                // 2. 注册到PNX
                try {
                    Registries.BLOCK.registerCustomBlock(plugin, blockClass);
                    log.info("[PNX] Successfully registered block: " + blockDef.id());
                } catch (RegisterException e) {
                    log.error("[PNX] RegisterException for block: " + blockDef.id());
                    log.error("[PNX]   Error: " + e.getMessage());
                }

            } catch (Exception e) {
                log.error("[PNX] Failed to register block: " + blockDef.id());
                log.error("[PNX]   Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    @Override
    public void registerEntities(List<EntityDef> entities) {
        log.info("[PNX] registerEntities size=" + entities.size());

        if (plugin == null) {
            log.error("[PNX] Plugin instance not initialized, cannot register entities");
            return;
        }

        for (EntityDef entityDef : entities) {
            try {
                log.info("[PNX] Registering entity: " + entityDef.id());

                // 1. 使用ByteBuddy生成Entity类
                Class<? extends cn.nukkit.entity.Entity> entityClass = entityGenerator.generateEntityClass(entityDef);

                // 2. 注册到PNX
                try {
                    Registries.ENTITY.registerCustomEntity(plugin, entityClass);
                    log.info("[PNX] Successfully registered entity: " + entityDef.id());
                } catch (RegisterException e) {
                    log.error("[PNX] RegisterException for entity: " + entityDef.id());
                    log.error("[PNX]   Error: " + e.getMessage());
                }

            } catch (Exception e) {
                log.error("[PNX] Failed to register entity: " + entityDef.id());
                log.error("[PNX]   Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    @Override
    public void registerRecipes(List<RecipeDef> recipes) {
        log.info("[PNX] registerRecipes size=" + recipes.size());
        // TODO: 实现Recipe注册
    }

    @Override
    public Capabilities capabilities() {
        return CAPS;
    }

    @Override
    public void setupResourcePackPushing(List<DeployedPack> deployedPacks, BridgeConfig config, Object plugin) {
        if (deployedPacks == null || deployedPacks.isEmpty()) {
            log.info("[PNX] No deployed packs to register");
            return;
        }

        log.info("[PNX] Setting up resource pack pushing for " + deployedPacks.size() + " pack(s)");

        try {
            // Get Server's ResourcePackManager
            cn.nukkit.Server server = cn.nukkit.Server.getInstance();
            if (server == null) {
                log.error("[PNX] Cannot get Server instance for resource pack registration");
                return;
            }

            cn.nukkit.resourcepacks.ResourcePackManager packManager = server.getResourcePackManager();
            if (packManager == null) {
                log.error("[PNX] ResourcePackManager is null");
                return;
            }

            // Create and register our custom loader
            PnxResourcePackLoader loader = new PnxResourcePackLoader(deployedPacks, config, log);
            packManager.registerPackLoader(loader);

            // Reload packs to apply changes
            log.info("[PNX] Reloading resource packs...");
            packManager.reloadPacks();

            log.info("[PNX] ✓ Resource pack pushing setup completed (universal client support)");

        } catch (Exception e) {
            log.error("[PNX] ✗ Failed to setup resource pack pushing");
            log.error("[PNX]   Error type: " + e.getClass().getSimpleName());
            log.error("[PNX]   Error message: " + e.getMessage());

            // Log stack trace
            log.error("[PNX]   Stack trace:");
            for (StackTraceElement element : e.getStackTrace()) {
                log.error("[PNX]     at " + element.toString());
                if (element.getClassName().contains("easecation.bridge")) {
                    break;
                }
            }
        }
    }

    @Override
    public void afterAllRegistrations() {
        log.info("[PNX] Performing post-registration tasks...");

        // 重建Entity NBT tag（PNX要求）
        try {
            Registries.ENTITY.rebuildTag();
            log.info("[PNX] Entity NBT tag rebuilt successfully");
        } catch (Exception e) {
            log.error("[PNX] Failed to rebuild entity NBT tag");
            log.error("[PNX]   Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        log.info("[PNX] Registration completed");
    }
}

