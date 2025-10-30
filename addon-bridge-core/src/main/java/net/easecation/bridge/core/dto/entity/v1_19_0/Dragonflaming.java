package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the dragon to use its flame breath attack. Can only be used by the Ender Dragon. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragonflaming(
    @JsonProperty("priority") @Nullable Integer priority,
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
