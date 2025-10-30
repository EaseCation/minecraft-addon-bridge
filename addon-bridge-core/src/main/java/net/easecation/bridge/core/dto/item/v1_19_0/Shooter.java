package net.easecation.bridge.core.dto.item.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Shooter Item Component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Shooter(
    /* Ammunition. */
    @JsonProperty("ammunition") @Nullable List<Object> ammunition,
    /* Charge on draw? Default is set to false. */
    @JsonProperty("charge_on_draw") @Nullable Boolean chargeOnDraw,
    /* Launch power scale. Default is set to 1.0. */
    @JsonProperty("launch_power_scale") @Nullable Double launchPowerScale,
    /* Draw Duration. Default is set to 0. */
    @JsonProperty("max_draw_duration") @Nullable Double maxDrawDuration,
    /* Launch power. Default is set to 1.0. */
    @JsonProperty("max_launch_power") @Nullable Double maxLaunchPower,
    /* Scale power by draw duration? Default is set to false. */
    @JsonProperty("scale_power_by_draw_duration") @Nullable Boolean scalePowerByDrawDuration
) {
}
