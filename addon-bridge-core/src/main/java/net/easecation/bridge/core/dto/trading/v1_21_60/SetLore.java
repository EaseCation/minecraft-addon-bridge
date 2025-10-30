package net.easecation.bridge.core.dto.trading.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set_potion. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetLore(
    /* This function sets the potion type of compatible items with a potion id.. */
    @JsonProperty("function") @Nullable String function,
    /* ID of the potion to set. */
    @JsonProperty("id") @Nullable String id
) {
}
