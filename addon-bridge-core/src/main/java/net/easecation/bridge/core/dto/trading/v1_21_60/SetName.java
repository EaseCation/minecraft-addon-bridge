package net.easecation.bridge.core.dto.trading.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set_name. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetName(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("name") @Nullable String name
) {
}
