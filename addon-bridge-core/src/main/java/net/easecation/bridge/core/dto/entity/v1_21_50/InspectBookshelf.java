package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to inspect bookshelves. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InspectBookshelf(
    /* Distance in blocks within the entity considers it has reached its target position. */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* The higher the priority, the sooner this behavior will be executed as a goal. */
    @JsonProperty("priority") @Nullable Integer priority,
    /* Movement speed multiplier. */
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier
) {
}
