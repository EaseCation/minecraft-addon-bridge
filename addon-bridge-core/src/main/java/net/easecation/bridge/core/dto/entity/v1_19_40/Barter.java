package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Enables the mob to barter for items that have been configured as barter currency. Must be used in combination with the barter component */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Barter(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
