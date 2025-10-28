package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;

/* A Jigsaw Structure is a group of Structure Templates that make up a larger structure. Jigsaw Structures are placed during world generation. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record JigsawDefinition(
    @JsonProperty("format_version") FormatVersion formatVersion,
    @JsonProperty("minecraft:jigsaw") Jigsaw minecraft_jigsaw
) {
}
