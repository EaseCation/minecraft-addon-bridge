package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the enderman to drop a block they are carrying. Can only be used by Endermen. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EndermanLeaveBlock(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
