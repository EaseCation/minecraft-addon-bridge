package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to randomly sit and look around for a duration. Note: Must have a sitting animation set up to use this. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomLookAroundAndSit(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The Maximum amount of unique looks a mob will have while looking around. */
    @JsonProperty("max_look_count") @Nullable Integer maxLookCount,
    /* The Maximum amount of time (in ticks) a mob will stay looking at a direction while looking around. */
    @JsonProperty("max_look_time") @Nullable Integer maxLookTime,
    /* The Minimum amount of unique looks a mob will have while looking around. */
    @JsonProperty("min_look_count") @Nullable Integer minLookCount,
    /* The Minimum amount of time (in ticks) a mob will stay looking at a direction while looking around. */
    @JsonProperty("min_look_time") @Nullable Integer minLookTime,
    /* The probability of randomly looking around/sitting. */
    @JsonProperty("probability") @Nullable Double probability
) {
}
