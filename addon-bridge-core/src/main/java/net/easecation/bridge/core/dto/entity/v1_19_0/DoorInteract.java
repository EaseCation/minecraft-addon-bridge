package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to drink potions based on specified environment conditions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DoorInteract(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
