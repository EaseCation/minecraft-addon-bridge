package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to lay an egg block on a sand block if the mob is pregnant. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LayEgg(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* [EXPERIMENTAL] Allows the mob to lay its eggs from below the target if it can't get there. This is useful if the target block is water with air above, since mobs may not be able to get to the air block above water. */
    @JsonProperty("allow_laying_from_below") @Nullable Boolean allowLayingFromBelow,
    /* [EXPERIMENTAL] Block type for the egg to lay. If this is a turtle egg, the number of eggs in the block is randomly set. */
    @JsonProperty("egg_type") @Nullable EntityJ eggType,
    /* Distance in blocks within the mob considers it has reached the goal. This is the "wiggle room" to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* [EXPERIMENTAL] Sound event name for laying egg. Defaulted to lay_egg which is used for Turtles. */
    @JsonProperty("lay_egg_sound") @Nullable String layEggSound,
    /* [EXPERIMENTAL] Duration of the laying egg process in seconds. */
    @JsonProperty("lay_seconds") @Nullable Double laySeconds,
    /* Event to run when this mob lays the egg. */
    @JsonProperty("on_lay") @Nullable Trigger onLay,
    /* Height in blocks the mob will look for a target block to move towards. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* The distance in blocks it will look for a target block to move towards. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* [EXPERIMENTAL] Blocks that the mob can lay its eggs on top of. */
    @JsonProperty("target_blocks") @Nullable List<EntityJ> targetBlocks,
    /* [EXPERIMENTAL] Types of materials that can exist above the target block. Valid types are Air, Water, and Lava. */
    @JsonProperty("target_materials_above_block") @Nullable String targetMaterialsAboveBlock,
    /* [EXPERIMENTAL] Specifies if the default lay-egg animation should be played when the egg is placed or not. */
    @JsonProperty("use_default_animation") @Nullable Boolean useDefaultAnimation
) {
}
