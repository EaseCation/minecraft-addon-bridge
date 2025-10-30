package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows mobs to occassionally stop and take a nap under certain conditions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Nap(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Maximum time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_max") @Nullable Double cooldownMax,
    /* Minimum time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_min") @Nullable Double cooldownMin,
    /* The block distance in x and z that will be checked for mobs that this mob detects. */
    @JsonProperty("mob_detect_dist") @Nullable Double mobDetectDist,
    /* The block distance in y that will be checked for mobs that this mob detects. */
    @JsonProperty("mob_detect_height") @Nullable Double mobDetectHeight,
    /* The filters that need to be met for the nap to take place. */
    @JsonProperty("can_nap_filters") @Nullable Filters canNapFilters,
    /* Filters that can trigger the entity to wake up from it nap. */
    @JsonProperty("wake_mob_exceptions") @Nullable Filters wakeMobExceptions
) {
}
