package net.easecation.bridge.core.dto.item.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Shoot(
    /* UNDOCUMENTED. */
    @JsonProperty("angle_offset") @Nullable Double angleOffset,
    /* UNDOCUMENTED. */
    @JsonProperty("launch_power") @Nullable Double launchPower,
    /* UNDOCUMENTED. */
    @JsonProperty("projectile") @Nullable String projectile
) {
}
