package net.easecation.bridge.core.dto.v1_21_60.behavior.trading;

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
