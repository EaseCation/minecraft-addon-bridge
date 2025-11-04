package net.easecation.bridge.adapter.pnx;

import cn.nukkit.item.Item;
import cn.nukkit.item.customitem.CustomItem;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.registry.ItemRuntimeIdRegistry;
import cn.nukkit.registry.Registries;
import me.sunlan.fastreflection.FastConstructor;
import me.sunlan.fastreflection.FastMemberLoader;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.ItemDef;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Legacy mode (version=0) item registrar for PowerNukkitX.
 *
 * <p>This class uses reflection to register Legacy mode items, bypassing the
 * standard Component-based registration flow. It's based on the reference
 * implementation in LegacyItemRegistrationHelper.
 *
 * <p><b>Warning:</b> This implementation uses reflection to access private fields
 * in PowerNukkitX's ItemRegistry. While this works with current PNX versions,
 * it may break in future versions if the internal implementation changes.
 *
 * <p><b>Note:</b> This simplified version doesn't use FastConstructor to avoid
 * compilation issues. Item instantiation is handled differently than the reference.
 */
public class LegacyItemRegistrar {

    /**
     * Register a Legacy mode (version=0) item to PowerNukkitX.
     *
     * @param plugin The plugin instance
     * @param itemClass The generated Item class (must implement CustomItem)
     * @param itemDef The ItemDef containing Legacy components
     * @param nbt The NBT data for the item (may be null for simple items)
     * @throws Exception if registration fails
     */
    public static void registerLegacyItem(
            Plugin plugin,
            Class<? extends Item> itemClass,
            ItemDef itemDef,
            CompoundTag nbt) throws Exception {

        String identifier = itemDef.id();

        BridgeLoggerHolder.getLogger().info("[LegacyItemRegistrar] Registering legacy item: " + identifier + " (version=0)");

        // 1. Validate item class implements CustomItem
        if (!CustomItem.class.isAssignableFrom(itemClass)) {
            throw new IllegalArgumentException("Item class must implement CustomItem interface: " + itemClass.getName());
        }

        // 2. Create CustomItemDefinition
        // IMPORTANT: Allocate runtime ID before creating definition to avoid ID=0 collision
        CustomItemDefinition.ensureRuntimeIdAllocated(identifier);
        CustomItemDefinition definition = new CustomItemDefinition(identifier, nbt);
        int runtimeId = definition.getRuntimeId();

        BridgeLoggerHolder.getLogger().info("[LegacyItemRegistrar] Created definition with runtimeId=" + runtimeId);

        // 3. Register to ItemRuntimeIdRegistry (isComponent = false generates version = 0)
        // This is the PUBLIC API - safe to use
        try {
            ItemRuntimeIdRegistry.RuntimeEntry legacyEntry = new ItemRuntimeIdRegistry.RuntimeEntry(
                identifier,
                runtimeId,
                false  // ← KEY: false = LEGACY format (version = 0)
            );
            Registries.ITEM_RUNTIMEID.registerCustomRuntimeItem(legacyEntry);
            BridgeLoggerHolder.getLogger().info("[LegacyItemRegistrar] Registered runtime entry for: " + identifier);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register runtime entry for: " + identifier, e);
        }

        // 4. Register to CACHE_CONSTRUCTORS (via reflection)
        // Since we can't create FastConstructor easily, we'll register a lambda-based constructor
        try {
            registerConstructor(identifier, itemClass, plugin);
            BridgeLoggerHolder.getLogger().debug("[LegacyItemRegistrar] Registered constructor for: " + identifier);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register item constructor for: " + identifier, e);
        }

        // 5. Register to CUSTOM_ITEM_DEFINITIONS (via reflection)
        // This stores the item definition for client synchronization
        try {
            registerDefinition(identifier, definition);
            BridgeLoggerHolder.getLogger().debug("[LegacyItemRegistrar] Registered definition for: " + identifier);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register item definition for: " + identifier, e);
        }

        // 6. Optional: Register to creative inventory
        try {
            if (nbt != null && Registries.CREATIVE.shouldBeRegisteredItem(nbt)) {
                CustomItem customItem = (CustomItem) itemClass.getDeclaredConstructor().newInstance();
                Item item = (Item) customItem;
                item.setNetId(null);
                int groupIndex = Registries.CREATIVE.resolveGroupIndexFromItemDefinition(identifier, nbt);
                Registries.CREATIVE.addCreativeItem(item, groupIndex);
                BridgeLoggerHolder.getLogger().info("[LegacyItemRegistrar] Added to creative inventory: " + identifier +
                    " (group " + groupIndex + ")");
            }
        } catch (Exception e) {
            // Creative registration is optional - log warning but don't fail
            BridgeLoggerHolder.getLogger().warning("[LegacyItemRegistrar] Failed to add to creative inventory: " +
                identifier + " - " + e.getMessage());
        }

        BridgeLoggerHolder.getLogger().info("[LegacyItemRegistrar] Successfully registered legacy item: " + identifier);
    }

    /**
     * Register item constructor to CACHE_CONSTRUCTORS using FastConstructor.
     * This follows the same approach as PNX's ItemRegistry.registerCustomItem.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void registerConstructor(String identifier, Class<? extends Item> itemClass, Plugin plugin) throws Exception {
        Field cacheConstructorsField = getItemRegistryField("CACHE_CONSTRUCTORS");
        Map cacheConstructors = (Map) cacheConstructorsField.get(null);

        try {
            // Create FastMemberLoader with plugin's ClassLoader
            // This matches PNX's approach in ItemRegistry (see fastMemberLoaderCache)
            FastMemberLoader memberLoader = new FastMemberLoader(plugin.getPluginClassLoader());

            // Get the no-arg constructor
            Constructor<? extends Item> itemConstructor = itemClass.getDeclaredConstructor();

            // Create FastConstructor directly (not through reflection)
            // This is the same approach used in PNX's ItemRegistry.registerCustomItem
            FastConstructor<? extends Item> fastConstructor = FastConstructor.create(
                itemConstructor,
                memberLoader,
                false  // false = don't skip security checks
            );

            // Register to cache
            if (cacheConstructors.putIfAbsent(identifier, fastConstructor) != null) {
                BridgeLoggerHolder.getLogger().warning("[LegacyItemRegistrar] Item constructor already registered: " + identifier);
            }

            BridgeLoggerHolder.getLogger().debug("[LegacyItemRegistrar] Registered FastConstructor for: " + identifier);

        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Item class must have a no-arg constructor: " + itemClass.getName(), e);
        } catch (Exception e) {
            BridgeLoggerHolder.getLogger().error("[LegacyItemRegistrar] Failed to create FastConstructor for: " + identifier);
            throw new RuntimeException("Failed to register item constructor for: " + identifier, e);
        }
    }

    /**
     * Register item definition to CUSTOM_ITEM_DEFINITIONS using reflection.
     */
    @SuppressWarnings("unchecked")
    private static void registerDefinition(String identifier, CustomItemDefinition definition) throws Exception {
        Field customItemDefsField = getItemRegistryField("CUSTOM_ITEM_DEFINITIONS");
        Map<String, CustomItemDefinition> customItemDefs =
            (Map<String, CustomItemDefinition>) customItemDefsField.get(null);

        customItemDefs.put(identifier, definition);
    }

    /**
     * Get a private static field from ItemRegistry using reflection.
     *
     * @param fieldName The field name to access
     * @return The reflected Field with setAccessible(true)
     * @throws Exception if reflection fails
     */
    private static Field getItemRegistryField(String fieldName) throws Exception {
        try {
            Class<?> itemRegistryClass = Class.forName("cn.nukkit.registry.ItemRegistry");
            Field field = itemRegistryClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ItemRegistry class not found - PNX version incompatible?", e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("ItemRegistry." + fieldName + " field not found - PNX version incompatible?", e);
        }
    }
}
