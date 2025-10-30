package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* Minecraft items */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemsDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:item") Item minecraft_item
) {
}
