package net.easecation.bridge.adapter.mot.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.custom.EntityDefinition;
import net.easecation.bridge.core.EntityDef;

/**
 * Builder utility to convert EntityDef to MOT's EntityDefinition.
 * Uses EntityDefinition's builder pattern to construct entity definitions.
 */
public class EntityDefinitionBuilder {

    /**
     * Build an EntityDefinition from EntityDef.
     * @param entityDef The EntityDef containing description and components
     * @param implClass The implementation class (EntityDataDriven.class)
     * @return Configured EntityDefinition
     */
    public static EntityDefinition build(EntityDef entityDef, Class<? extends Entity> implClass) {
        net.easecation.bridge.core.dto.v1_21_60.behavior.entities.Entity.Description description = entityDef.description();
        if (description == null) {
            throw new RuntimeException("EntityDef must have a description");
        }

        String identifier = description.identifier();
        if (identifier == null || identifier.isEmpty()) {
            throw new RuntimeException("EntityDef description must have an identifier");
        }

        // Build EntityDefinition using MOT's builder API
        EntityDefinition definition = EntityDefinition.builder()
            .identifier(identifier)
            .implementation(implClass)
            .spawnEgg(description.isSpawnable() != null ? description.isSpawnable() : false)
            .build();

        return definition;
    }
}
