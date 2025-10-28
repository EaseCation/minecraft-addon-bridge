package net.easecation.bridge.core.dto.v1_21_60.behavior.loot_tables;

import com.fasterxml.jackson.annotation.*;

/* A collection of items where the system will choice one or more from. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Pools(
) {
}
