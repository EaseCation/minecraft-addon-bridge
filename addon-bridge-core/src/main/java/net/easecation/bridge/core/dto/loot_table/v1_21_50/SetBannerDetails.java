package net.easecation.bridge.core.dto.loot_table.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set<i>banner</i>details. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetBannerDetails(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("type") @Nullable Integer type
) {
}
