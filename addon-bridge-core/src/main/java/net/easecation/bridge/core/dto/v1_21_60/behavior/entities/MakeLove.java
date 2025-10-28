package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the villager to look for a mate to spawn other villagers with. Can only be used by Villagers. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MakeLove(
    @JsonProperty("priority") @Nullable Priority priority
) {
}
