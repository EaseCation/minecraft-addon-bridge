package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Causes an entity to circle around an anchor point placed near a point or target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CircleAroundAnchor(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Horizontal distance from the anchor point this entity must stay within upon a successful radius adjustment. */
    @JsonProperty("radius_range") @Nullable Range_a_B radiusRange,
    /* A random value to determine when to increase the size of the radius up to the maximum. This has a 1/value chance every tick to do so. */
    @JsonProperty("radius_change_chance") @Nullable Integer radiusChangeChance,
    /* The number of blocks above the target that the next anchor point can be set. This value is used only when the entity is tracking a target. */
    @JsonProperty("height_above_target_range") @Nullable Range_a_B heightAboveTargetRange,
    /* The range of height in blocks offset the mob can have from it's anchor point. */
    @JsonProperty("height_offset_range") @Nullable Range_a_B heightOffsetRange,
    /* A random value to determine when to change the height of the mob from the anchor point. This has a 1/value chance every tick to do so. */
    @JsonProperty("height_change_chance") @Nullable Integer heightChangeChance,
    /* Maximum distance from the anchor-point in which this entity considers itself to have reached the anchor point. This is to prevent the entity from bouncing back and forth trying to reach a specific spot. */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* The number of blocks to increase the current movement radius by, upon successful {@code radius<i>adjustment</i>chance}. If the current radius increases over the range maximum, the current radius will be set back to the range minimum and the entity will change between clockwise and counter-clockwise movement. */
    @JsonProperty("radius_change") @Nullable Double radiusChange,
    /* Percent chance to determine how often to increase the size of the current movement radius around the anchor point. 1 = 100%. {@code radius<i>change</i>chance} is deprecated and has been replaced with {@code radius<i>adjustment</i>chance}. */
    @JsonProperty("radius_adjustment_chance") @Nullable Double radiusAdjustmentChance,
    /* Percent chance to determine how often to increase or decrease the current height around the anchor point. 1 = 100%. {@code height<i>change</i>chance} is deprecated and has been replaced with {@code height<i>adjustment</i>chance}. */
    @JsonProperty("height_adjustment_chance") @Nullable Double heightAdjustmentChance,
    /* Number of degrees to change this entity's facing by, when the entity selects its next anchor point. */
    @JsonProperty("angle_change") @Nullable Double angleChange
) {
}
