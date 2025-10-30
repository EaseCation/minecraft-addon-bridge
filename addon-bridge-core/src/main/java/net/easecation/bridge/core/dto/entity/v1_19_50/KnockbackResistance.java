package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Determines the amount of knockback resistance that the item has. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record KnockbackResistance(
    /* Percentage of knockback to reduce with 1.0 being 100% reduction. */
    @JsonProperty("value") @Nullable Double value
) {
}
