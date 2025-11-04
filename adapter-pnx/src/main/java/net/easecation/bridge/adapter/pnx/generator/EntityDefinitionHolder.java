package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.entity.custom.CustomEntityDefinition;
import net.easecation.bridge.adapter.pnx.mapper.EntityDefinitionBuilder;
import net.easecation.bridge.core.EntityDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储EntityDef和对应的CustomEntityDefinition的holder
 */
public class EntityDefinitionHolder {

    private static final Map<String, EntityDef> ENTITY_DEFS = new ConcurrentHashMap<>();
    private static final Map<String, CustomEntityDefinition> DEFINITIONS_CACHE = new ConcurrentHashMap<>();
    private static EntityDefinitionBuilder builder;

    public static void initialize(EntityDefinitionBuilder definitionBuilder) {
        builder = definitionBuilder;
    }

    public static void registerEntityDef(String entityId, EntityDef entityDef) {
        ENTITY_DEFS.put(entityId, entityDef);
    }

    public static CustomEntityDefinition getDefinition(String entityId) {
        return DEFINITIONS_CACHE.computeIfAbsent(entityId, id -> {
            EntityDef entityDef = ENTITY_DEFS.get(id);
            if (entityDef == null) {
                throw new IllegalStateException("EntityDef not found for: " + id);
            }
            // entityInstance 参数传 null，因为不需要实体实例来构建 definition
            return builder.build(entityDef, null);
        });
    }

    public static EntityDef getEntityDef(String entityId) {
        return ENTITY_DEFS.get(entityId);
    }
}
