package net.easecation.bridge.core.dto.block.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* The minecraft block behavior specification. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlocksDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:block") BlockDefinitions minecraft_block
) {
}
