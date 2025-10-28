package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.processors;

import com.fasterxml.jackson.annotation.*;

/* Rules used by Jigsaw Structures to determine which blocks to modify or replace when placing a Structure Template in the world. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProcessorListDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:processor_list") ProcessorList minecraft_processorList
) {
}
