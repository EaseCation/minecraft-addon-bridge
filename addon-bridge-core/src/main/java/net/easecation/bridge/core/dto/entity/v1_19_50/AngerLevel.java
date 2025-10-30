package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this entity to track anger towards a set of nuisances */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AngerLevel(
    /* Anger level will decay over time. Defines how often anger towards all nuisances will be decreased by one */
    @JsonProperty("anger_decrement_interval") @Nullable Double angerDecrementInterval,
    /* Anger boost applied to angry threshold when mob gets angry. */
    @JsonProperty("angry_boost") @Nullable Integer angryBoost,
    /* Threshold that define when the mob is considered angry at a nuisance. */
    @JsonProperty("angry_threshold") @Nullable Integer angryThreshold,
    /* The default amount of annoyingness for any given nuisance. Specifies how much to raise anger level on each provocation */
    @JsonProperty("default_annoyingness") @Nullable String defaultAnnoyingness,
    /* The maximum anger level that can be reached. Applies to any nuisance */
    @JsonProperty("max_anger") @Nullable Integer maxAnger,
    /* Filter that is applied to determine if a mob can be a nuisance. */
    @JsonProperty("nuisance_filter") @Nullable Filters nuisanceFilter,
    @JsonProperty("on_increase_sounds") @Nullable Filters onIncreaseSounds,
    /* Defines if the mob should remove target if it falls below 'angry' threshold. */
    @JsonProperty("remove_targets_below_angry_threshold") @Nullable Boolean removeTargetsBelowAngryThreshold
) {
}
