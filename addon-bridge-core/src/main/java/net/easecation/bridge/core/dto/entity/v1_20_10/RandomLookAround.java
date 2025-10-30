package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to randomly look around. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomLookAround(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The range of time in seconds the mob will stay looking in a random direction before looking elsewhere */
    @JsonProperty("look_time") @Nullable VectorOf2Items lookTime,
    /* The rightmost angle a mob can look at on the horizontal plane with respect to its initial facing direction. */
    @JsonProperty("max_angle_of_view_horizontal") @Nullable Integer maxAngleOfViewHorizontal,
    /* The leftmost angle a mob can look at on the horizontal plane with respect to its initial facing direction. */
    @JsonProperty("min_angle_of_view_horizontal") @Nullable Integer minAngleOfViewHorizontal
) {
}
