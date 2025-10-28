package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the entity's 'angry' state using a timer. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Angry(
    /* If true, other entities of the same entity definition within the broadcastRange will also become angry. */
    @JsonProperty("broadcast_anger") @Nullable Boolean broadcastAnger,
    /* Conditions that make this entry in the list valid. */
    @JsonProperty("broadcast_filters") @Nullable Filters broadcastFilters,
    /* Filter out mob types that it should not attack while angry (other Piglins). */
    @JsonProperty("filters") @Nullable Filters filters,
    /* Distance in blocks within which other entities of the same entity definition will become angry. */
    @JsonProperty("broadcast_range") @Nullable Integer broadcastRange,
    /* A list of entity families to broadcast anger to. */
    @JsonProperty("broadcast_targets") @Nullable List<String> broadcastTargets,
    /* Event to run after the number of seconds specified in duration expires (when the entity stops being "angry") */
    @JsonProperty("calm_event") @Nullable Event calmEvent,
    /* The sound event to play when the mob is angry. */
    @JsonProperty("angry_sound") @Nullable SoundEvent angrySound,
    /* If true, other entities of the same entity definition within the broadcastRange will also become angry whenever this mob attacks. */
    @JsonProperty("broadcast_anger_on_attack") @Nullable Boolean broadcastAngerOnAttack,
    /* If true, other entities of the same entity definition within the broadcastRange will also become angry whenever this mob is attacked. */
    @JsonProperty("broadcast_anger_on_being_attacked") @Nullable Boolean broadcastAngerOnBeingAttacked,
    /* If false, when this mob is killed it does not spread its anger to other entities of the same entity definition within the broadcastRange */
    @JsonProperty("broadcast_anger_when_dying") @Nullable Boolean broadcastAngerWhenDying,
    /* The amount of time in seconds that the entity will be angry. */
    @JsonProperty("duration") @Nullable Integer duration,
    /* Variance in seconds added to the duration [-delta, delta]. */
    @JsonProperty("duration_delta") @Nullable Integer durationDelta,
    /* The range of time in seconds to randomly wait before playing the sound again. */
    @JsonProperty("sound_interval") @Nullable Object soundInterval
) {
}
