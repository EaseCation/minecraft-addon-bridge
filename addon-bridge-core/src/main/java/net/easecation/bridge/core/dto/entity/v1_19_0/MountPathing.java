package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to move around on its own while mounted seeking a target to attack. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MountPathing(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The distance at which this mob wants to be away from its target. */
    @JsonProperty("target_dist") @Nullable Double targetDist,
    /* If true, this mob will chase after the target as long as it's a valid target. */
    @JsonProperty("track_target") @Nullable Boolean trackTarget
) {
}
