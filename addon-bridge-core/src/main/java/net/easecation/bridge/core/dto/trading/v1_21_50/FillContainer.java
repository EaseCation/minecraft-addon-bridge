package net.easecation.bridge.core.dto.trading.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function fill_container. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FillContainer(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("loot_table") @Nullable String lootTable
) {
}
