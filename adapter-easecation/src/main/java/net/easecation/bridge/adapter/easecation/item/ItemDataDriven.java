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
            // Branch on registration mode
            if (itemDef.isLegacy()) {
                this.definition = initLegacyItem(itemDef, identifier);
            } else {
                this.definition = initComponentBasedItem(itemDef, identifier);
            }

        } catch (Exception e) {
            String errorMsg = "Failed to initialize ItemDefinition for item '" + identifier + "' (itemId=" + itemId + ")";
            System.err.println("[ItemDataDriven] " + errorMsg);
            System.err.println("[ItemDataDriven] Error type: " + e.getClass().getSimpleName());
            System.err.println("[ItemDataDriven] Error message: " + e.getMessage());

            if (itemDef.componentComponents() != null) {
                System.err.println("[ItemDataDriven] Item has components: YES");
            } else {
                System.err.println("[ItemDataDriven] Item has components: NO");
            }

            e.printStackTrace();
            throw new RuntimeException(errorMsg, e);
        }
    }

    /**
     * Initialize item definition from Legacy mode (v1.10) components.
     */
    private ItemDefinition initLegacyItem(ItemDef itemDef, String identifier) {
        var components = itemDef.legacyComponents();

        // Default values
        int maxStackSize = 64;
        int maxDurability = 0;
        boolean canAlwaysEat = false;
        int nutrition = 0;
        float saturationModifier = 0.0f;
        String usingConvertsTo = null;
        boolean hasFood = false;
        int armorProtection = 0;
        String armorSlot = null;

        if (components != null) {
            // Max stack size
            if (components.getMaxStackSize() != null && components.getMaxStackSize().value() != null) {
                maxStackSize = components.getMaxStackSize().value();
            }

            // Food properties
            if (components.getFood() != null) {
                var food = components.getFood();
                hasFood = true;

                if (food.canAlwaysEat() != null) {
                    canAlwaysEat = food.canAlwaysEat();
                }
                if (food.nutrition() != null) {
                    nutrition = food.nutrition();
                }
                if (food.saturationModifier() != null) {
                    saturationModifier = parseSaturationModifier(food.saturationModifier());
                }
                if (food.usingConvertsTo() != null) {
                    usingConvertsTo = food.usingConvertsTo();
                }
            }

            // Armor properties (Netease extension)
            if (components.getNeteaseArmor() != null) {
                var armor = components.getNeteaseArmor();
                if (armor.defense() != null) {
                    armorProtection = armor.defense();
                }
                if (armor.armorSlot() != null) {
                    armorSlot = mapArmorSlotToString(armor.armorSlot());
                }
            }
        }

        return new ItemDefinition(
            identifier,
            identifier,  // Legacy mode doesn't have display_name component
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
    }

    /**
     * Parse saturation modifier string to float value.
     * Maps Bedrock's named values to numeric coefficients.
     */
    private static float parseSaturationModifier(String modifier) {
        if (modifier == null) return 0.6f;
        return switch (modifier.toLowerCase()) {
            case "poor" -> 0.1f;
            case "low" -> 0.3f;
            case "normal" -> 0.6f;
            case "good" -> 0.8f;
            case "max" -> 1.0f;
            case "supernatural" -> 1.2f;
            default -> 0.6f;
        };
    }

    /**
     * Map Legacy mode armor slot integer to slot string.
     * Legacy: 0=head, 1=chest, 2=legs, 3=feet
     * Component-based: "slot.armor.head", "slot.armor.chest", etc.
     */
    private static String mapArmorSlotToString(Integer slotId) {
        if (slotId == null) return null;
        return switch (slotId) {
            case 0 -> "slot.armor.head";
            case 1 -> "slot.armor.chest";
            case 2 -> "slot.armor.legs";
            case 3 -> "slot.armor.feet";
            default -> "slot.armor.head"; // Default to helmet
        };
    }

    /**
     * Initialize item definition from Component-based mode (v1.19+) components.
     */
    private ItemDefinition initComponentBasedItem(ItemDef itemDef, String identifier) {
        var components = itemDef.componentComponents();

        if (components == null) {
            // Minimal item with default properties
            return new ItemDefinition(
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

        return new ItemDefinition(
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
