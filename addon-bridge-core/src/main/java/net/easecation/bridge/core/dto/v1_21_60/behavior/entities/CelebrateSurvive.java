package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this entity to celebrate surviving a raid by shooting fireworks. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CelebrateSurvive(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier,
    /* Minimum and maximum time between firework (positive, in seconds). */
    @JsonProperty("fireworks_interval") @Nullable Range_a_B_ fireworksInterval,
    /* The duration in seconds that the celebration lasts for. */
    @JsonProperty("duration") @Nullable Double duration,
    /* The event to trigger when the goal's duration expires. */
    @JsonProperty("on_celebration_end_event") @Nullable Event onCelebrationEndEvent
) {
}
