package net.easecation.bridge.core;

import net.easecation.bridge.core.dto.v1_21_60.behavior.entities.Entity;
import net.easecation.bridge.core.dto.v1_21_60.behavior.entities.Components;
import net.easecation.bridge.core.dto.v1_21_60.behavior.entities.Events;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Represents a custom entity definition parsed from behavior pack.
 * Contains full entity description, components, component groups and events from DTO.
 */
public record EntityDef(
        String id,
        @Nullable Entity.Description description,
        @Nullable Components components,
        @Nullable Map<String, Components> componentGroups,
        @Nullable Events events,
        // Legacy fields for backward compatibility
        double health,
        double speed,
        @Nullable Map<String, Object> ai
) {
    // Legacy constructor for backward compatibility
    public EntityDef(String id, double health, double speed, Map<String, Object> ai) {
        this(id, null, null, null, null, health, speed, ai);
    }

    // Constructor from DTO
    public static EntityDef fromDTO(Entity dto) {
        String identifier = dto.description() != null && dto.description().identifier() != null
                ? dto.description().identifier().toString()
                : "unknown:entity";

        return new EntityDef(
                identifier,
                dto.description(),
                dto.components(),
                dto.componentGroups(),
                dto.events(),
                0.0,
                0.0,
                null
        );
    }
}

