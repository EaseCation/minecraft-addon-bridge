package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines how much exhaustion each player action should take. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ExhaustionValues(
    /* Amount of exhaustion applied when attacking. */
    @JsonProperty("attack") @Nullable Double attack,
    /* Amount of exhaustion applied when taking damage. */
    @JsonProperty("damage") @Nullable Double damage,
    /* Amount of exhaustion applied when healed through food regeneration. */
    @JsonProperty("heal") @Nullable Double heal,
    /* Amount of exhaustion applied when jumping. */
    @JsonProperty("jump") @Nullable Double jump,
    /* Amount of exhaustion applied when mining. */
    @JsonProperty("mine") @Nullable Double mine,
    /* Amount of exhaustion applied when sprinting. */
    @JsonProperty("sprint") @Nullable Double sprint,
    /* Amount of exhaustion applied when sprint jumping. */
    @JsonProperty("sprint_jump") @Nullable Double sprintJump,
    /* Amount of exhaustion applied when swimming. */
    @JsonProperty("swim") @Nullable Double swim,
    /* Amount of exhaustion applied when walking. */
    @JsonProperty("walk") @Nullable Double walk
) {
}
