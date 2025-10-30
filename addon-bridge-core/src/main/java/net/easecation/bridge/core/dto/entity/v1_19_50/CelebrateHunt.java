package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Specifies hunt celebration behavior. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CelebrateHunt(
    /* If true, celebration will be broadcasted to other entities in the radius. */
    @JsonProperty("broadcast") @Nullable Boolean broadcast,
    /* The list of conditions that target of hunt must satisfy to initiate celebration. */
    @JsonProperty("celebration_targets") @Nullable Filters celebrationTargets,
    /* The sound event to play when the mob is celebrating. */
    @JsonProperty("celebrate_sound") @Nullable String celebrateSound,
    /* Duration, in seconds, of celebration. */
    @JsonProperty("duration") @Nullable Integer duration,
    /* If broadcast is enabled, specifies the radius in which it will notify other entities for celebration. */
    @JsonProperty("radius") @Nullable Double radius,
    /* The range of time in seconds to randomly wait before playing the sound again. */
    @JsonProperty("sound_interval") @Nullable Object soundInterval
) {
}
