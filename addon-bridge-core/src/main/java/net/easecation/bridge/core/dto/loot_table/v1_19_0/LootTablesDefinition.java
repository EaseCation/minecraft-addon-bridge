package net.easecation.bridge.core.dto.loot_table.v1_19_0;

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
