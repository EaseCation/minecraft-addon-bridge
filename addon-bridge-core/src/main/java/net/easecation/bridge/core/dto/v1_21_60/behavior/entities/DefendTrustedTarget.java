package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to target another mob that hurts an entity it trusts. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DefendTrustedTarget(
    @JsonProperty("priority") @Nullable Priority priority,
    /* Sound to occasionally play while defending. */
    @JsonProperty("aggro_sound") @Nullable SoundEvent aggroSound,
    /* Time in seconds between attacks. */
    @JsonProperty("attack_interval") @Nullable Integer attackInterval,
    /* If true, only entities in this mob's viewing range can be selected as targets. */
    @JsonProperty("must_see") @Nullable Boolean mustSee,
    /* Determines the amount of time in seconds that this mob will look for a target before forgetting about it and looking for a new one when the target isn't visible any more. */
    @JsonProperty("must_see_forget_duration") @Nullable Double mustSeeForgetDuration,
    /* The event to run when this mob starts to defend the entity it trusts. */
    @JsonProperty("on_defend_start") @Nullable Event onDefendStart,
    /* Distance in blocks that the target can be within to launch an attack. */
    @JsonProperty("within_radius") @Nullable Double withinRadius,
    /* List of entity types that this mob considers valid targets. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* Probability that a sound will play. */
    @JsonProperty("sound_chance") @Nullable Double soundChance
) {
}
