package net.easecation.bridge.core.dto.trading.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set_data. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetData(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("data") @Nullable Object data
) {
}
