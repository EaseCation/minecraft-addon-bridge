package net.easecation.bridge.core.dto.loot_table.v1_19_40;

import com.fasterxml.jackson.annotation.*;

/* A minecraft loot table condition. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Condition(
) {
}
