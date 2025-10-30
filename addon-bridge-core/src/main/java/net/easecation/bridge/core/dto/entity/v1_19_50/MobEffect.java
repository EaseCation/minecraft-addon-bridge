package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A component that applies a mob effect to entities that get within range. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MobEffect(
    /* Time in seconds to wait between each application of the effect. */
    @JsonProperty("cooldown_time") @Nullable Integer cooldownTime,
    /* How close a hostile entity must be to have the mob effect applied. */
    @JsonProperty("effect_range") @Nullable Double effectRange,
    /* How long the applied mob effect lasts in seconds. */
    @JsonProperty("effect_time") @Nullable Integer effectTime,
    /* Filter to use for conditions. */
    @JsonProperty("entity_filter") @Nullable Filters entityFilter,
    /* The mob effect that is applied to entities that enter this entities effect range. */
    @JsonProperty("mob_effect") @Nullable String mobEffect
) {
}
