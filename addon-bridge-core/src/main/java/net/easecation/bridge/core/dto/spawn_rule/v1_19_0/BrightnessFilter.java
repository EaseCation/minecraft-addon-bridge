package net.easecation.bridge.core.dto.spawn_rule.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
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
