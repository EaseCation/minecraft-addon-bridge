package net.easecation.bridge.adapter.pm1e.item;

import cn.nukkit.item.Item;
import cn.nukkit.item.custom.CustomItem;
import cn.nukkit.item.custom.ItemDefinition;
import net.easecation.bridge.core.ItemDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data-driven custom item implementation for PM1E Nukkit.
 * Loads item properties dynamically from ItemDef based on Bedrock behavior pack format.
 *
 * <p>PM1E uses CustomItem interface with getItemDefinition() method.
 * This class maintains a static registry to support multiple items with dynamic class generation.</p>
 */
public class ItemDataDriven extends Item implements CustomItem {

    // Static registry to map identifier to ItemDef
    private static final Map<String, ItemDef> ITEM_DEF_REGISTRY = new ConcurrentHashMap<>();

    // Static registry to map identifier to ItemDefinition
    private static final Map<String, ItemDefinition> DEFINITION_CACHE = new ConcurrentHashMap<>();

    private final String identifier;

    /**
     * Constructor for data-driven items.
     *
     * @param nukkitId The numeric ID for this item (5000-65534)
     * @param identifier The item identifier (e.g., "namespace:item_name")
     * @param displayName The display name (can be null)
     */
    public ItemDataDriven(int nukkitId, String identifier, String displayName) {
        super(nukkitId, 0, 1, displayName != null ? displayName : identifier);
        this.identifier = identifier;
    }

    /**
     * Register an ItemDef for a given identifier.
     */
    public static void registerItemDef(String identifier, ItemDef itemDef) {
        ITEM_DEF_REGISTRY.put(identifier, itemDef);
    }

    /**
     * Register a pre-built ItemDefinition for a given identifier.
     */
    public static void registerDefinition(String identifier, ItemDefinition definition) {
        DEFINITION_CACHE.put(identifier, definition);
    }

    /**
     * Get the ItemDef for a given identifier.
     */
    public static ItemDef getItemDef(String identifier) {
        return ITEM_DEF_REGISTRY.get(identifier);
    }

    /**
     * PM1E required method: return the ItemDefinition for this item.
     * This is called by PM1E to get the item's properties.
     */
    @Override
    public ItemDefinition getItemDefinition() {
        // Return the cached definition
        ItemDefinition cached = DEFINITION_CACHE.get(identifier);
        if (cached != null) {
            return cached;
        }

        throw new RuntimeException("ItemDataDriven: ItemDefinition not found for identifier '" + identifier + "'");
    }

    @Override
    public int getMaxStackSize() {
        // Get from ItemDef if available
        ItemDef itemDef = ITEM_DEF_REGISTRY.get(identifier);
        if (itemDef != null) {
            if (itemDef.isLegacy()) {
                // Legacy模式 (v1.10)
                var components = itemDef.legacyComponents();
                if (components != null && components.getMaxStackSize() != null
                    && components.getMaxStackSize().value() != null) {
                    return components.getMaxStackSize().value();
                }
            } else {
                // Component-based模式 (v1.19+)
                if (itemDef.componentComponents() != null
                    && itemDef.componentComponents().minecraft_maxStackSize() != null) {
                    net.easecation.bridge.core.dto.item.v1_21_60.MaxStackSize maxStackSize =
                        itemDef.componentComponents().minecraft_maxStackSize();
                    if (maxStackSize instanceof net.easecation.bridge.core.dto.item.v1_21_60.MaxStackSize.MaxStackSize_Variant0 variant0) {
                        return variant0.value().intValue();
                    }
                }
            }
        }
        return super.getMaxStackSize();
    }

    @Override
    public int getMaxDurability() {
        // Get from ItemDef if available
        ItemDef itemDef = ITEM_DEF_REGISTRY.get(identifier);
        if (itemDef != null) {
            // Legacy模式暂不支持耐久度
            if (!itemDef.isLegacy()) {
                // Component-based模式 (v1.19+)
                if (itemDef.componentComponents() != null
                    && itemDef.componentComponents().minecraft_durability() != null) {
                    net.easecation.bridge.core.dto.item.v1_21_60.Durability durability =
                        itemDef.componentComponents().minecraft_durability();
                    if (durability.maxDurability() != null) {
                        return durability.maxDurability();
                    }
                }
            }
        }
        return super.getMaxDurability();
    }

    @Override
    public boolean allowOffhand() {
        // Get from ItemDef if available
        ItemDef itemDef = ITEM_DEF_REGISTRY.get(identifier);
        if (itemDef != null && !itemDef.isLegacy()) {
            // Component-based模式
            // Fix: Use Record accessor method instead of field
            if (itemDef.componentComponents() != null
                && itemDef.componentComponents().minecraft_handEquipped() != null) {
                var handEquipped = itemDef.componentComponents().minecraft_handEquipped();

                // HandEquipped is a sealed interface with two variants
                if (handEquipped instanceof net.easecation.bridge.core.dto.item.v1_21_60.HandEquipped.HandEquipped_Variant0 variant0) {
                    return variant0.value() != null && variant0.value();
                } else if (handEquipped instanceof net.easecation.bridge.core.dto.item.v1_21_60.HandEquipped.HandEquipped_Variant1 variant1) {
                    return variant1.value() != null && variant1.value();
                }
            }
        }
        return super.allowOffhand();
    }
}
