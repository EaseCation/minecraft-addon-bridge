package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the enderman to take a block and carry it around. Can only be used by Endermen. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EndermanTakeBlock(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
