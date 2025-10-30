package net.easecation.bridge.core.dto.trading.v1_20_41;

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
