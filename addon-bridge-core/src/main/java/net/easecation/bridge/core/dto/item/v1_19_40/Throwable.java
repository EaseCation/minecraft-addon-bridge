package net.easecation.bridge.core.dto.item.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Throwable item component. Throwable items, such as a snowball. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Throwable(
    /* Whether the item should use the swing animation when thrown. Default is set to false. */
    @JsonProperty("do_swing_animation") @Nullable Boolean doSwingAnimation,
    /* The scale at which the power of the throw increases. Default is set to 1.0. */
    @JsonProperty("launch_power_scale") @Nullable Double launchPowerScale,
    /* The maximum duration to draw a throwable item. Default is set to 0.0. */
    @JsonProperty("max_draw_duration") @Nullable Double maxDrawDuration,
    /* The minimum duration to draw a throwable item. Default is set to 0.0. */
    @JsonProperty("min_draw_duration") @Nullable Double minDrawDuration,
    /* The maximum power to launch the throwable item. Default is set to 1.0. */
    @JsonProperty("max_launch_power") @Nullable Double maxLaunchPower,
    /* Whether or not the power of the throw increases with duration charged. Default is set to false. */
    @JsonProperty("scale_power_by_draw_duration") @Nullable Boolean scalePowerByDrawDuration
) {
}
