package net.easecation.bridge.core.dto.v1_21_60.behavior.loot_tables;

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
