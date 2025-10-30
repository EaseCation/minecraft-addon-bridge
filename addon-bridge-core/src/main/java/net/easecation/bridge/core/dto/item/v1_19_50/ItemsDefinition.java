package net.easecation.bridge.core.dto.item.v1_19_50;

import com.fasterxml.jackson.annotation.*;

/* Minecraft items 1.16.200 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemsDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:item") Item minecraft_item
) {
}
