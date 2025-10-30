package net.easecation.bridge.core;

import net.easecation.bridge.core.dto.entity.v1_21_60.Entity;
import net.easecation.bridge.core.dto.entity.v1_21_60.Components;
import net.easecation.bridge.core.dto.entity.v1_21_60.Events;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Represents a custom entity definition parsed from behavior pack.
 * Uses the LATEST version (v1_21_60) - old versions will be auto-upgraded in the future.
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

    // Constructor from DTO (always uses latest version)
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

    // TODO: In the future, add auto-upgrade support:
    // public static EntityDef fromJSON(String json) {
    //     // 1. Detect format_version
    //     // 2. Load corresponding DTO version
    //     // 3. Upgrade to latest version (v1_21_60)
    //     // 4. Create EntityDef from latest DTO
    // }
}

