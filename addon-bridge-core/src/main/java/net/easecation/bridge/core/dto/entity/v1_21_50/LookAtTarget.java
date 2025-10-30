package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to look at the entity they are targetting. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LookAtTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The distance in blocks from which the entity will look at this mob's current target. */
    @JsonProperty("look_distance") @Nullable Double lookDistance,
    /* The probability of looking at the target. A value of 1.00 is 100%. */
    @JsonProperty("probability") @Nullable Double probability,
    /* Time range to look at this mob's current target. */
    @JsonProperty("look_time") @Nullable Range_a_B lookTime,
    /* The angle in degrees that the mob can see in the X-axis (left-right). */
    @JsonProperty("angle_of_view_vertical") @Nullable Integer angleOfViewVertical,
    /* The angle in degrees that the mob can see in the Y-axis (up-down). */
    @JsonProperty("angle_of_view_horizontal") @Nullable Integer angleOfViewHorizontal
) {
}
