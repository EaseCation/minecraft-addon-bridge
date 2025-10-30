package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;

/* sets the loot table for what items this entity drops upon death. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Loot(
    /* The path to the loot table, relative to the Behavior Pack's root. */
    @JsonProperty("table") String table
) {
}
