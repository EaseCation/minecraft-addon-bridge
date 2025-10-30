package net.easecation.bridge.core.dto.loot_table.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* The function enchant<i>book</i>for_trading. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EnchantBookForTrading(
    /* UNDOCUMENTED. */
    @JsonProperty("function") String function,
    /* UNDOCUMENTED. */
    @JsonProperty("base_cost") Integer baseCost,
    /* UNDOCUMENTED. */
    @JsonProperty("base_random_cost") Integer baseRandomCost,
    /* UNDOCUMENTED. */
    @JsonProperty("per_level_random_cost") Integer perLevelRandomCost,
    /* UNDOCUMENTED. */
    @JsonProperty("per_level_cost") Integer perLevelCost
) {
}
