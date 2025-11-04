package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.item.customitem.CustomItemDefinition;
import net.easecation.bridge.adapter.pnx.mapper.ItemDefinitionBuilder;
import net.easecation.bridge.core.ItemDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储ItemDef和对应的CustomItemDefinition的holder
 */
public class ItemDefinitionHolder {

    private static final Map<String, ItemDef> ITEM_DEFS = new ConcurrentHashMap<>();
    private static final Map<String, CustomItemDefinition> DEFINITIONS_CACHE = new ConcurrentHashMap<>();
    private static ItemDefinitionBuilder builder;

    public static void initialize(ItemDefinitionBuilder definitionBuilder) {
        builder = definitionBuilder;
    }

    public static void registerItemDef(String itemId, ItemDef itemDef) {
        ITEM_DEFS.put(itemId, itemDef);
    }

    public static <T extends cn.nukkit.item.Item & cn.nukkit.item.customitem.CustomItem> CustomItemDefinition getDefinition(String itemId, T itemInstance) {
        return DEFINITIONS_CACHE.computeIfAbsent(itemId, id -> {
            ItemDef itemDef = ITEM_DEFS.get(id);
            if (itemDef == null) {
                throw new IllegalStateException("ItemDef not found for: " + id);
            }
            // itemInstance 现在已经是正确的类型
            return builder.build(itemDef, itemInstance);
        });
    }

    public static ItemDef getItemDef(String itemId) {
        return ITEM_DEFS.get(itemId);
    }
}
