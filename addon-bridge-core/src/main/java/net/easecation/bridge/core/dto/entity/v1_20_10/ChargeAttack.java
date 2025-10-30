package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this entity to damage a target by using a running attack. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ChargeAttack(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* A charge attack cannot start if the entity is farther than this distance to the target. */
    @JsonProperty("max_distance") @Nullable Double maxDistance,
    /* A charge attack cannot start if the entity is closer than this distance to the target. */
    @JsonProperty("min_distance") @Nullable Double minDistance,
    /* Percent chance this entity will start a charge attack, if not already attacking (1.0 = 100%) */
    @JsonProperty("success_rate") @Nullable Double successRate
) {
}
