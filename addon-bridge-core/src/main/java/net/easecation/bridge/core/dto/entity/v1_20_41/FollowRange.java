package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the range of blocks that a mob will pursue a target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FollowRange(
    /* Range of the amount of damage the melee follow_range deals. A negative value can heal the entity instead of hurting it. */
    @JsonProperty("value") @Nullable Integer value,
    /* Duration, in seconds, of the status ailment applied to the damaged entity. */
    @JsonProperty("max") @Nullable Integer max
) {
}
