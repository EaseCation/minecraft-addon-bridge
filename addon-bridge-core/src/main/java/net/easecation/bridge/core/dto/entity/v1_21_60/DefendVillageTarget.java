package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the entity to stay in a village and defend the village from aggressors. If a player is in bad standing with the village this goal will cause the entity to attack the player regardless of filter conditions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DefendVillageTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    /* List of entity types this mob considers a threat to the village. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* The entity must be able to reach attacker. */
    @JsonProperty("must_reach") @Nullable Boolean mustReach,
    /* The percentage chance that the entity has to attack aggressors of its village, where 1.0 = 100%. */
    @JsonProperty("attack_chance") @Nullable Double attackChance
) {
}
