package net.easecation.bridge.core.dto.v1_21_60.behavior.trading;

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
