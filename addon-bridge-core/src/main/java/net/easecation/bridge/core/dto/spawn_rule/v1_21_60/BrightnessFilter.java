package net.easecation.bridge.core.dto.spawn_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This component allows players to set the light level range that causes the mob to spawn. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BrightnessFilter(
    /* This is the minimum light level value that allows the mob to spawn. */
    @JsonProperty("min") @Nullable Integer min,
    /* This is the maximum light level value that allows the mob to spawn. */
    @JsonProperty("max") @Nullable Integer max,
    /* This determines if weather can affect the light level conditions that cause the mob to spawn (e.g. Allowing hostile mobs to spawn during the day when it rains.) */
    @JsonProperty("adjust_for_weather") @Nullable Boolean adjustForWeather
) {
}
