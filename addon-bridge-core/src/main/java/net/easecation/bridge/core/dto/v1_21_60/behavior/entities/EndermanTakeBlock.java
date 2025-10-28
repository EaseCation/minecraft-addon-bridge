package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the enderman to take a block and carry it around. Can only be used by Endermen. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EndermanTakeBlock(
    @JsonProperty("priority") @Nullable Priority priority
) {
}
