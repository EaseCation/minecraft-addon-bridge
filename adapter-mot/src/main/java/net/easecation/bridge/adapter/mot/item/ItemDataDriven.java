package net.easecation.bridge.adapter.mot.item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustom;
import net.easecation.bridge.core.ItemDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data-driven custom item implementation for MOT Nukkit.
 * Loads item properties dynamically from ItemDef based on Bedrock behavior pack format.
 *
 * <p>MOT uses ItemCustom with getDefinition() method to build CustomItemDefinition.
 * This class maintains a static registry to support multiple items with a single class.</p>
 */
public class ItemDataDriven extends ItemCustom {

    // Static registry to map identifier to ItemDef
    private static final Map<String, ItemDef> ITEM_DEF_REGISTRY = new ConcurrentHashMap<>();

    // Static registry to map identifier to CustomItemDefinition
    private static final Map<String, CustomItemDefinition> DEFINITION_CACHE = new ConcurrentHashMap<>();

    private final String identifier;
    private final String textureName;

    /**
     * Constructor for Legacy mode items (two-parameter version).
     * When textureName is not specified, ItemCustom will use displayName as textureName automatically.
     * This is used for Legacy mode (v1.10) items where textures are defined client-side.
     *
     * @param identifier The item identifier (e.g., "namespace:item_name")
     * @param displayName The display name (can be null)
     */
    public ItemDataDriven(String identifier, String displayName) {
        super(identifier, displayName);
        this.identifier = identifier;
        // ItemCustom's two-parameter constructor will use displayName as textureName
        this.textureName = displayName;
    }

    /**
     * Constructor for Component-based mode items (three-parameter version).
     * Used for Component-based mode (v1.19+) items where textures are defined server-side.
     *
     * @param identifier The item identifier (e.g., "namespace:item_name")
     * @param displayName The display name (can be null)
     * @param textureName The texture name
     */
    public ItemDataDriven(String identifier, String displayName, String textureName) {
        super(identifier, displayName, textureName);
        this.identifier = identifier;
        this.textureName = textureName;
    }

    /**
     * Register an ItemDef for a given identifier.
     */
    public static void registerItemDef(String identifier, ItemDef itemDef) {
        ITEM_DEF_REGISTRY.put(identifier, itemDef);
    }

    /**
     * Register a pre-built CustomItemDefinition for a given identifier.
     */
    public static void registerDefinition(String identifier, CustomItemDefinition definition) {
        DEFINITION_CACHE.put(identifier, definition);
    }

    /**
     * MOT required method: return the CustomItemDefinition for this item.
     * This is called by MOT to get the item's properties.
     */
    @Override
    public CustomItemDefinition getDefinition() {
        // First check if we have a cached definition
        CustomItemDefinition cached = DEFINITION_CACHE.get(identifier);
        if (cached != null) {
            return cached;
        }

        // If not cached, build it from ItemDef
        ItemDef itemDef = ITEM_DEF_REGISTRY.get(identifier);
        if (itemDef == null) {
            throw new RuntimeException("ItemDataDriven: ItemDef not found for identifier '" + identifier + "'");
        }

        // Build CustomItemDefinition using MOT's builder
        CustomItemDefinition definition = ItemDefinitionBuilder.build(this, itemDef);

        // Cache for future use
        DEFINITION_CACHE.put(identifier, definition);

        return definition;
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
            // Legacy模式暂不支持耐久度（minecraft:max_damage组件）
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
}
