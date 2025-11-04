package net.easecation.bridge.adapter.pm1e.entity;

import cn.nukkit.entity.custom.EntityDefinition;
import net.easecation.bridge.core.EntityDef;

/**
 * Builder for PM1E EntityDefinition from EntityDef.
 */
public class EntityDefinitionBuilder {

    public static EntityDefinition build(
        Class<? extends EntityDataDriven> entityClass,
        EntityDef entityDef
    ) {
        String identifier = entityDef.id();

        var builder = EntityDefinition.builder()
            .identifier(identifier)
            .implementation(entityClass);

        return builder.build();
    }
}
