package net.easecation.bridge.adapter.easecation.item;

import cn.nukkit.item.Item;
import net.easecation.bridge.core.ItemDef;
import net.easecation.bridge.core.dto.item.v1_21_60.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data-driven custom item implementation for EaseCation Nukkit.
 * Loads item properties dynamically from ItemDef based on Bedrock behavior pack format.
 */
public class ItemDataDriven extends Item {

    // Static registry to map runtime ID to ItemDef
    private static final Map<Integer, ItemDef> ITEM_DEF_REGISTRY = new ConcurrentHashMap<>();

    private ItemDefinition definition;

    public ItemDataDriven(int id) {
        this(id, 0, 1);
    }

    public ItemDataDriven(Integer meta) {
        this(meta, 1);
    }

    public ItemDataDriven(Integer meta, int count) {
        super(0, meta, count);
        // ID will be set during registration
    }

    public ItemDataDriven(int id, Integer meta, int count) {
        super(id, meta, count, "Unknown Item");
        tryInitItemDefinition();
        // Note: Item name is set in constructor and cannot be changed afterward
        // The display name will be used when rendering to the client
    }

    /**
     * Register an ItemDef for a given item ID.
     */
    public static void registerItemDef(int itemId, ItemDef itemDef) {
        ITEM_DEF_REGISTRY.put(itemId, itemDef);
    }

    protected void tryInitItemDefinition() {
        if (this.definition != null) {
            return;
        }

        int itemId = this.getId();
        ItemDef itemDef = ITEM_DEF_REGISTRY.get(itemId);
        if (itemDef == null) {
            throw new RuntimeException("ItemDataDriven: ItemDef not found for itemId " + itemId +
                ". Registry size: " + ITEM_DEF_REGISTRY.size() +
                ". Available IDs: " + ITEM_DEF_REGISTRY.keySet());
        }

        String identifier = itemDef.id();
        try {
            var components = itemDef.components();

            if (components == null) {
                // Minimal item with default properties
                this.definition = new ItemDefinition(
                    identifier,           // identifier
                    identifier,           // displayName
                    64,                   // maxStackSize
                    0,                    // maxDurability
                    false,                // canAlwaysEat
                    0,                    // nutrition
                    0.0f,                 // saturationModifier
                    null,                 // usingConvertsTo
                    0,                    // armorProtection
                    null,                 // armorSlot
                    false                 // hasFood
                );
                return;
            }

            // Extract properties from components
            String displayName = extractDisplayName(components, identifier);
            int maxStackSize = extractMaxStackSize(components);
            int maxDurability = extractMaxDurability(components);

            // Extract food properties
            boolean canAlwaysEat = false;
            int nutrition = 0;
            float saturationModifier = 0.0f;
            String usingConvertsTo = null;
            boolean hasFood = false;

            if (components.minecraft_food() != null) {
                var food = components.minecraft_food();
                hasFood = true;

                if (food.canAlwaysEat() != null) {
                    canAlwaysEat = food.canAlwaysEat();
                }

                if (food.nutrition() != null) {
                    nutrition = food.nutrition().intValue();
                }

                if (food.saturationModifier() != null) {
                    saturationModifier = food.saturationModifier().floatValue();
                }

                if (food.usingConvertsTo() != null) {
                    usingConvertsTo = food.usingConvertsTo();
                }
            }

            // Extract armor properties
            int armorProtection = 0;
            String armorSlot = null;

            if (components.minecraft_wearable() != null) {
                var wearable = components.minecraft_wearable();

                if (wearable.protection() != null) {
                    armorProtection = wearable.protection();
                }

                if (wearable.slot() != null) {
                    armorSlot = wearable.slot();
                }
            }

            this.definition = new ItemDefinition(
                identifier,
                displayName,
                maxStackSize,
                maxDurability,
                canAlwaysEat,
                nutrition,
                saturationModifier,
                usingConvertsTo,
                armorProtection,
                armorSlot,
                hasFood
            );

        } catch (Exception e) {
            String errorMsg = "Failed to initialize ItemDefinition for item '" + identifier + "' (itemId=" + itemId + ")";
            System.err.println("[ItemDataDriven] " + errorMsg);
            System.err.println("[ItemDataDriven] Error type: " + e.getClass().getSimpleName());
            System.err.println("[ItemDataDriven] Error message: " + e.getMessage());

            if (itemDef.components() != null) {
                System.err.println("[ItemDataDriven] Item has components: YES");
            } else {
                System.err.println("[ItemDataDriven] Item has components: NO");
            }

            e.printStackTrace();
            throw new RuntimeException(errorMsg, e);
        }
    }

    private String extractDisplayName(net.easecation.bridge.core.dto.item.v1_21_60.Item.Components components, String fallback) {
        if (components.minecraft_displayName() != null) {
            var displayName = components.minecraft_displayName();
            if (displayName.value() != null) {
                return displayName.value();
            }
        }
        return fallback;
    }

    private int extractMaxStackSize(net.easecation.bridge.core.dto.item.v1_21_60.Item.Components components) {
        if (components.minecraft_maxStackSize() != null) {
            Double value = extractDoubleValue(components.minecraft_maxStackSize());
            if (value != null) {
                return value.intValue();
            }
        }
        return 64; // Default stack size
    }

    private Double extractDoubleValue(Object component) {
        // Handle sealed interfaces with Variant0/Variant1 that have a Double value
        try {
            var method = component.getClass().getMethod("value");
            Object result = method.invoke(component);
            if (result instanceof Double) {
                return (Double) result;
            }
        } catch (Exception e) {
            // Ignore and return null
        }
        return null;
    }

    private String extractStringValue(Object component) {
        // Handle sealed interfaces with Variant0 that have a String value
        try {
            var method = component.getClass().getMethod("value");
            Object result = method.invoke(component);
            if (result instanceof String) {
                return (String) result;
            }
        } catch (Exception e) {
            // Ignore and return null
        }
        return null;
    }

    private int extractMaxDurability(net.easecation.bridge.core.dto.item.v1_21_60.Item.Components components) {
        if (components.minecraft_durability() != null) {
            var durability = components.minecraft_durability();
            if (durability.maxDurability() != null) {
                return durability.maxDurability();
            }
        }
        return 0; // No durability
    }

    // Override Item methods

    @Override
    public int getMaxStackSize() {
        return definition != null ? definition.maxStackSize : 64;
    }

    @Override
    public int getMaxDurability() {
        return definition != null ? definition.maxDurability : 0;
    }

    @Override
    public boolean canBeActivated() {
        // Items with food component can be activated (eaten)
        return definition != null && definition.hasFood;
    }

    /**
     * Internal item definition holding all properties.
     */
    public static class ItemDefinition {
        // === Basic Identification ===
        public final String identifier;
        public final String displayName;

        // === Stack and Durability ===
        public final int maxStackSize;
        public final int maxDurability;

        // === Food Properties ===
        public final boolean canAlwaysEat;
        public final int nutrition;
        public final float saturationModifier;
        public final String usingConvertsTo;

        // === Armor Properties ===
        public final int armorProtection;
        public final String armorSlot; // "slot.armor.head", "slot.armor.chest", etc.

        // === Component Flags ===
        public final boolean hasFood;

        public ItemDefinition(String identifier, String displayName,
                            int maxStackSize, int maxDurability,
                            boolean canAlwaysEat, int nutrition, float saturationModifier,
                            String usingConvertsTo, int armorProtection, String armorSlot,
                            boolean hasFood) {
            this.identifier = identifier;
            this.displayName = displayName;
            this.maxStackSize = maxStackSize;
            this.maxDurability = maxDurability;
            this.canAlwaysEat = canAlwaysEat;
            this.nutrition = nutrition;
            this.saturationModifier = saturationModifier;
            this.usingConvertsTo = usingConvertsTo;
            this.armorProtection = armorProtection;
            this.armorSlot = armorSlot;
            this.hasFood = hasFood;
        }
    }
}
