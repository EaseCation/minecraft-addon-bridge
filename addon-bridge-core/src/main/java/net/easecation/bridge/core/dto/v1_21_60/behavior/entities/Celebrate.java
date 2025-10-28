package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this entity to celebrate surviving a raid by making celebration sounds and jumping. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Celebrate(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The sound event to trigger during the celebration. */
    @JsonProperty("celebration_sound") @Nullable String celebrationSound,
    /* The duration in seconds that the celebration lasts for. */
    @JsonProperty("duration") @Nullable Double duration,
    /* Minimum and maximum time between jumping (positive, in seconds). */
    @JsonProperty("jump_interval") @Nullable Object jumpInterval,
    /* The event to trigger when the goal's duration expires. */
    @JsonProperty("on_celebration_end_event") @Nullable Trigger onCelebrationEndEvent,
    /* Minimum and maximum time between sound events (positive, in seconds). */
    @JsonProperty("sound_interval") @Nullable Range_a_B_ soundInterval
) {
}
