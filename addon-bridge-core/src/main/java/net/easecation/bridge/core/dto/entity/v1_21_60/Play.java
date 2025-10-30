package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to play with other baby villagers. This can only be used by Villagers. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Play(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Percent chance that the mob will start this goal, from 0 to 1. */
    @JsonProperty("chance_to_start") @Nullable Double chanceToStart,
    /* The distance (in blocks) that the mob tries to be in range of the friend it's following. */
    @JsonProperty("follow_distance") @Nullable Integer followDistance,
    /* The dimensions of the AABB used to search for a potential friend to play with. */
    @JsonProperty("friend_search_area") @Nullable VectorOf3Items friendSearchArea,
    /* The entity type(s) to consider when searching for a potential friend to play with. */
    @JsonProperty("friend_types") @Nullable List<Object> friendTypes,
    /* The max amount of seconds that the mob will play for before exiting the Goal. */
    @JsonProperty("max_play_duration_seconds") @Nullable Double maxPlayDurationSeconds,
    /* The height (in blocks) that the mob will search within to find a random position position to move to. Must be at least 1. */
    @JsonProperty("random_pos_search_height") @Nullable Integer randomPosSearchHeight,
    /* The distance (in blocks) on ground that the mob will search within to find a random position to move to. Must be at least 1. */
    @JsonProperty("random_pos_search_range") @Nullable Integer randomPosSearchRange
) {
}
