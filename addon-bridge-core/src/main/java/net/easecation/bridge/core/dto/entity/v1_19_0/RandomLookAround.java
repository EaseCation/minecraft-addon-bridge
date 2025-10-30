package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to randomly look around. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomLookAround(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The range of time in seconds the mob will stay looking in a random direction before looking elsewhere. */
    @JsonProperty("look_time") @Nullable Object lookTime,
    /* The distance in blocks from which the mob will look at. */
    @JsonProperty("look_distance") @Nullable Double lookDistance,
    /* The probability of looking at the target. A value of 1.00 is 100%. */
    @JsonProperty("probability") @Nullable Double probability,
    /* The angle in degrees that the mob can see in the X-axis (left-right). */
    @JsonProperty("angle_of_view_vertical") @Nullable Integer angleOfViewVertical,
    /* The angle in degrees that the mob can see in the Y-axis (up-down). */
    @JsonProperty("angle_of_view_horizontal") @Nullable Integer angleOfViewHorizontal
) {
}
