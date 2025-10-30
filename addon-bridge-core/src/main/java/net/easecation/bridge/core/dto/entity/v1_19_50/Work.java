package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the NPC to use the POI. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Work(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The amount of ticks the NPC will stay in their the work location. */
    @JsonProperty("active_time") @Nullable Integer activeTime,
    /* If true, this entity can work when their jobsite POI is being rained on. */
    @JsonProperty("can_work_in_rain") @Nullable Boolean canWorkInRain,
    /* The amount of ticks the goal will be on cooldown before it can be used again. */
    @JsonProperty("goal_cooldown") @Nullable Integer goalCooldown,
    /* Event to run when the mob reaches their jobsite. */
    @JsonProperty("on_arrival") @Nullable Trigger onArrival,
    /* The max interval in which a sound will play. */
    @JsonProperty("sound_delay_max") @Nullable Integer soundDelayMax,
    /* The min interval in which a sound will play. */
    @JsonProperty("sound_delay_min") @Nullable Integer soundDelayMin,
    /* If "can<i>work</i>in_rain" is false, this is the maximum number of ticks left in the goal where rain will not interrupt the goal */
    @JsonProperty("work_in_rain_tolerance") @Nullable Integer workInRainTolerance
) {
}
