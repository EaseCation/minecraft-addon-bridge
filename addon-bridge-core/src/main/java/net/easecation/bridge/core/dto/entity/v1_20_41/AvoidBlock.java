package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows this entity to avoid certain blocks. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AvoidBlock(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Should start tick interval. */
    @JsonProperty("tick_interval") @Nullable Integer tickInterval,
    /* Maximum distance to look for a block in xz. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* Maximum distance to look for a block in y. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* Modifier for sprint speed. 1.0 means keep the regular speed, while higher numbers make the sprint speed faster. */
    @JsonProperty("sprint_speed_modifier") @Nullable Double sprintSpeedModifier,
    /* Block search method. */
    @JsonProperty("target_selection_method") @Nullable String targetSelectionMethod,
    /* List of block types this mob avoids. */
    @JsonProperty("target_blocks") @Nullable List<EntityJ> targetBlocks,
    /* The sound event to play when the mob is avoiding a block. */
    @JsonProperty("avoid_block_sound") @Nullable String avoidBlockSound,
    /* Modifier for walking speed. 1.0 means keep the regular speed, while higher numbers make the walking speed faster. */
    @JsonProperty("walk_speed_modifier") @Nullable Double walkSpeedModifier,
    /* Escape trigger. */
    @JsonProperty("on_escape") @Nullable List<Event> onEscape,
    /* The range of time in seconds to randomly wait before playing the sound again. */
    @JsonProperty("sound_interval") @Nullable Object soundInterval
) {
}
