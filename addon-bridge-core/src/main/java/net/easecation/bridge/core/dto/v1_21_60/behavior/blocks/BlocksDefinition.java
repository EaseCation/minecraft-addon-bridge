package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

/* The minecraft block behavior specification. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlocksDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:block") BlockDefinitions minecraft_block
) {
}
