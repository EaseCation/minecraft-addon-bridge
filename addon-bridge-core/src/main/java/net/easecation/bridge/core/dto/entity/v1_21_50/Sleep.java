package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows mobs that own a bed to in a village to move to and sleep in it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Sleep(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* If true, the mob will be able to use the sleep goal if riding something. */
    @JsonProperty("can_sleep_while_riding") @Nullable Boolean canSleepWhileRiding,
    /* Time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* The height of the mob's collider while sleeping. */
    @JsonProperty("sleep_collider_height") @Nullable Double sleepColliderHeight,
    /* The width of the mob's collider while sleeping. */
    @JsonProperty("sleep_collider_width") @Nullable Double sleepColliderWidth,
    /* The y offset of the mob's collider while sleeping. */
    @JsonProperty("sleep_y_offset") @Nullable Double sleepYOffset,
    /* The cooldown time in seconds before the goal can be reused after a internal failure or timeout condition. */
    @JsonProperty("timeout_cooldown") @Nullable Double timeoutCooldown,
    /* Distance in blocks within the mob considers it has reached the goal. This is the "wiggle room" to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius
) {
}
