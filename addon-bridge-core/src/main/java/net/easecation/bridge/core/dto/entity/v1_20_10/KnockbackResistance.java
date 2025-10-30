package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to resist being knocked backwards by a melee attack. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record KnockbackResistance(
    /* Percentage of knockback to reduce with 1.0 being 100% reduction. */
    @JsonProperty("value") @Nullable Double value,
    /* The maximum amount of knockback resistance that can be applied to the entity. */
    @JsonProperty("maximum") @Nullable Double maximum
) {
}
