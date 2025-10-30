package net.easecation.bridge.core.dto.loot_table.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function specific_enchants. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpecificEnchants(
    /* Specific enchants. */
    @JsonProperty("function") @Nullable String function,
    /* A enchanting specification. */
    @JsonProperty("enchants") @Nullable Object enchants
) {
}
