package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this entity to use a flame-breath attack. Note: This behavior can only be used by the ender_dragon entity type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragonflaming(
    @JsonProperty("priority") @Nullable Priority priority,
    /* Time (in seconds), after roar, to breath flame. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* Time (in seconds), after roar, to breath flame. */
    @JsonProperty("flame_time") @Nullable Double flameTime,
    /* Number of ground flame-breath attacks to use before flight-takeoff. */
    @JsonProperty("ground_flame_count") @Nullable Integer groundFlameCount,
    /* Time (in seconds) to roar, before breathing flame. */
    @JsonProperty("roar_time") @Nullable Double roarTime
) {
}
