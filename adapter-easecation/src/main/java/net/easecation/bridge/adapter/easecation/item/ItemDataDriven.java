package net.easecation.bridge.adapter.easecation.item;

import cn.nukkit.item.Item;
import net.easecation.bridge.core.ItemDef;
import net.easecation.bridge.core.dto.v1_21_60.behavior.items.*;

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
            Map<String, Object> components = itemDef.components();

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

            Object foodObj = components.get("minecraft:food");
            if (foodObj instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> foodMap = (Map<String, Object>) foodObj;
                hasFood = true;

                Object canAlwaysEatObj = foodMap.get("can_always_eat");
                if (canAlwaysEatObj instanceof Boolean) {
                    canAlwaysEat = (Boolean) canAlwaysEatObj;
                }

                Object nutritionObj = foodMap.get("nutrition");
                if (nutritionObj instanceof Number) {
                    nutrition = ((Number) nutritionObj).intValue();
                }

                Object saturationObj = foodMap.get("saturation_modifier");
                if (saturationObj instanceof Number) {
                    saturationModifier = ((Number) saturationObj).floatValue();
                }

                Object convertsToObj = foodMap.get("using_converts_to");
                if (convertsToObj instanceof String) {
                    usingConvertsTo = (String) convertsToObj;
                }
            }

            // Extract armor properties
            int armorProtection = 0;
            String armorSlot = null;

            Object wearableObj = components.get("minecraft:wearable");
            if (wearableObj instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> wearableMap = (Map<String, Object>) wearableObj;

                Object protectionObj = wearableMap.get("protection");
                if (protectionObj instanceof Number) {
                    armorProtection = ((Number) protectionObj).intValue();
                }

                Object slotObj = wearableMap.get("slot");
                if (slotObj instanceof String) {
                    armorSlot = (String) slotObj;
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

    private String extractDisplayName(Map<String, Object> components, String fallback) {
        Object displayName = components.get("minecraft:display_name");
        if (displayName instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> displayNameMap = (Map<String, Object>) displayName;
            Object value = displayNameMap.get("value");
            if (value instanceof String) {
                return (String) value;
            }
        } else if (displayName instanceof String) {
            return (String) displayName;
        }
        return fallback;
    }

    private int extractMaxStackSize(Map<String, Object> components) {
        Object maxStackSize = components.get("minecraft:max_stack_size");
        if (maxStackSize instanceof Number) {
            // Direct number value (Variant0)
            return ((Number) maxStackSize).intValue();
        } else if (maxStackSize instanceof Map) {
            // Object with value field (Variant1)
            @SuppressWarnings("unchecked")
            Map<String, Object> maxStackSizeMap = (Map<String, Object>) maxStackSize;
            Object value = maxStackSizeMap.get("value");
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
        }
        return 64; // Default stack size
    }

    private int extractMaxDurability(Map<String, Object> components) {
        Object durability = components.get("minecraft:durability");
        if (durability instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> durabilityMap = (Map<String, Object>) durability;
            Object maxDurability = durabilityMap.get("max_durability");
            if (maxDurability instanceof Number) {
                return ((Number) maxDurability).intValue();
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
