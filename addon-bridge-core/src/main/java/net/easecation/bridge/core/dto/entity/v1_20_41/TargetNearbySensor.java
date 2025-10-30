package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the entity's range within which it can see or sense other entities to target them. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TargetNearbySensor(
    /* Whether the other entity needs to be visible to trigger {@code inside} events. */
    @JsonProperty("must_see") @Nullable Boolean mustSee,
    /* Maximum distance in blocks that another entity will be considered in the {@code inside} range. */
    @JsonProperty("inside_range") @Nullable Double insideRange,
    /* Event to call when an entity gets in the inside range. Can specify {@code event} for the name of the event and {@code target} for the target of the event */
    @JsonProperty("on_inside_range") @Nullable Event onInsideRange,
    /* Event to call when an entity gets in the outside range. Can specify {@code event} for the name of the event and {@code target} for the target of the event */
    @JsonProperty("on_outside_range") @Nullable Event onOutsideRange,
    /* Event to call when an entity exits visual range. Can specify {@code event} for the name of the event and {@code target} for the target of the event */
    @JsonProperty("on_vision_lost_inside_range") @Nullable Event onVisionLostInsideRange,
    /* Maximum distance in blocks that another entity will be considered in the {@code outside} range. */
    @JsonProperty("outside_range") @Nullable Double outsideRange
) {
}
