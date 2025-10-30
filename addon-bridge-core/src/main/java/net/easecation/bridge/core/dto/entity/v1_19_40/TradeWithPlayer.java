package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the player to trade with this mob. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TradeWithPlayer(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
