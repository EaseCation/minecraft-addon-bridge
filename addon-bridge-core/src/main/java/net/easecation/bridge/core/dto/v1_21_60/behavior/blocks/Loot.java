package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

/* The path to the loot table, relative to the behavior pack. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Loot(
    /* The path to the loot table, relative to the behavior pack. */
    String value
) {
}
