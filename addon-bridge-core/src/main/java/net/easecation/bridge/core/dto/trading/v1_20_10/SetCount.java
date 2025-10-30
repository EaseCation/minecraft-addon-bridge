package net.easecation.bridge.core.dto.trading.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set_count. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetCount(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("count") @Nullable Object count
) {
}
