package net.easecation.bridge.core.dto.loot_table.v1_19_0;

import com.fasterxml.jackson.annotation.*;

/* A collection of items where the system will choice one or more from. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Pools(
) {
}
