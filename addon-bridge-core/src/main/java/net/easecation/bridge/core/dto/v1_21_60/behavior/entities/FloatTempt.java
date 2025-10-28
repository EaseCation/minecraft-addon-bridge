package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows an entity to be tempted by a set item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FloatTempt(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* If true, the mob can stop being tempted if the player moves too fast while close to this mob. */
    @JsonProperty("can_get_scared") @Nullable Boolean canGetScared,
    /* If true, the mob can be tempted even if it has a passenger (i.e. if being ridden). */
    @JsonProperty("can_tempt_while_ridden") @Nullable Boolean canTemptWhileRidden,
    /* If true, vertical distance to the player will be considered when tempting. */
    @JsonProperty("can_tempt_vertically") @Nullable Boolean canTemptVertically,
    /* List of items this mob is tempted by. */
    @JsonProperty("items") @Nullable List<EntitiesBb> items,
    /* Range of random ticks to wait between tempt sounds. */
    @JsonProperty("sound_interval") @Nullable Object soundInterval,
    /* The distance at which the mob will stop following the player. */
    @JsonProperty("stop_distance") @Nullable Double stopDistance,
    /* Sound to play while the mob is being tempted. */
    @JsonProperty("tempt_sound") @Nullable String temptSound,
    /* Distance in blocks this mob can get tempted by a player holding an item they like. */
    @JsonProperty("within_radius") @Nullable Double withinRadius,
    /* Specifies the event to trigger when the goal starts */
    @JsonProperty("on_start") @Nullable Trigger onStart,
    /* Specifies the event to trigger when the goal ends */
    @JsonProperty("on_end") @Nullable Trigger onEnd
) {
}
