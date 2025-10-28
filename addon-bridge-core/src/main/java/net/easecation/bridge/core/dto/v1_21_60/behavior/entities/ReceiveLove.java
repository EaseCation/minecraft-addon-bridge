package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the villager to stop so another villager can breed with it. Can only be used by a Villager. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ReceiveLove(
    @JsonProperty("priority") @Nullable Priority priority
) {
}
