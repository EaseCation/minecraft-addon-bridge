package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.structure_sets;

import com.fasterxml.jackson.annotation.*;

/* A Jigsaw Structure Set is a collection of Jigsaw Structures that are placed according to a set of rules. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StructureSetDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:structure_set") StructureSet minecraft_structureSet
) {
}
