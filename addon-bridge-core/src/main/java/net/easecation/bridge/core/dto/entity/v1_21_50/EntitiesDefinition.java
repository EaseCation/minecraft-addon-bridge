package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;

/* The minecraft entity behavior specification. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntitiesDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:entity") Entity minecraft_entity
) {
}
