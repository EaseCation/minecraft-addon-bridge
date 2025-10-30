package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the actor to break doors assuming that that flags set up for the component to use in navigation. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AnnotationBreakDoor(
    /* The time in seconds required to break through doors. */
    @JsonProperty("break_time") @Nullable Double breakTime,
    /* The minimum difficulty that the world must be on for this entity to break doors. */
    @JsonProperty("min_difficulty") @Nullable String minDifficulty
) {
}
