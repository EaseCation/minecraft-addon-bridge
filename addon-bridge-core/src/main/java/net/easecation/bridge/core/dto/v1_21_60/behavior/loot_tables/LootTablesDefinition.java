package net.easecation.bridge.core.dto.v1_21_60.behavior.loot_tables;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A minecraft loot table. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LootTablesDefinition(
    /* UNDOCUMENTED. */
    @JsonProperty("pools") @Nullable Pools pools,
    /* UNDOCUMENTED. */
    @JsonProperty("type") @Nullable String type
) {
}
