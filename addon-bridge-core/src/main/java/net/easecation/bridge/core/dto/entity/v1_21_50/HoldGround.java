package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The mob freezes and looks at the mob they are targeting. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HoldGround(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Whether to broadcast out the mob's target to other mobs of the same type. */
    @JsonProperty("broadcast") @Nullable Boolean broadcast,
    /* Range in blocks for how far to broadcast. */
    @JsonProperty("broadcast_range") @Nullable Double broadcastRange,
    /* Minimum distance the target must be for the mob to run this goal. */
    @JsonProperty("min_radius") @Nullable Double minRadius,
    /* Event to run when target is within the radius. This event is broadcasted if broadcast is true. */
    @JsonProperty("within_radius_event") @Nullable Event withinRadiusEvent
) {
}
