package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* Minecraft items */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemsDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:item") Item minecraft_item
) {
}
