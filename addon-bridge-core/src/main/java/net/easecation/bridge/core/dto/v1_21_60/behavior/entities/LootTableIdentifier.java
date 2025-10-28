package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* A minecraft loot_table identifier. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LootTableIdentifier(
    /* A minecraft loot_table identifier. */
    String value
) {
}
