package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;

/* The minecraft biome behavior specification. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BiomesDefinition(
    @JsonProperty("format_version") FormatVersion formatVersion,
    @JsonProperty("minecraft:biome") BiomeDefinition minecraft_biome
) {
}
