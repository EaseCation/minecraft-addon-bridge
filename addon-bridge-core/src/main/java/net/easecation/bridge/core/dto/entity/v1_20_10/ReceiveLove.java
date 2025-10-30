package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the villager to stop so another villager can breed with it. Can only be used by a Villager. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ReceiveLove(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
