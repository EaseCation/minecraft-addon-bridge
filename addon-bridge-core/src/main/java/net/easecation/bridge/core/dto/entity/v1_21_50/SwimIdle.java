package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the entity go idle, if swimming. Entity must be in water. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SwimIdle(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Amount of time (in seconds) to stay idle. */
    @JsonProperty("idle_time") @Nullable Double idleTime,
    /* Percent chance this entity will go idle, 1.0 = 100%. */
    @JsonProperty("success_rate") @Nullable Double successRate
) {
}
