package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines how the entity explodes. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Explode(
    /* If true, the explosion will affect blocks and entities under water. */
    @JsonProperty("allow_underwater") @Nullable Boolean allowUnderwater,
    /* If true, the explosion will destroy blocks in the explosion radius. */
    @JsonProperty("breaks_blocks") @Nullable Boolean breaksBlocks,
    /* If true, blocks in the explosion radius will be set on fire. */
    @JsonProperty("causes_fire") @Nullable Boolean causesFire,
    /* A scale factor applied to the explosion's damage to entities. A value of 0 prevents the explosion from dealing any damage. Negative values cause the explosion to heal entities instead. */
    @JsonProperty("damage_scaling") @Nullable Double damageScaling,
    /* If true, whether the explosion breaks blocks is affected by the mob griefing game rule. */
    @JsonProperty("destroy_affected_by_griefing") @Nullable Boolean destroyAffectedByGriefing,
    /* If true, whether the explosion causes fire is affected by the mob griefing game rule. */
    @JsonProperty("fire_affected_by_griefing") @Nullable Boolean fireAffectedByGriefing,
    /* The range for the random amount of time the fuse will be lit before exploding, a negative value means the explosion will be immediate. */
    @JsonProperty("fuse_length") @Nullable Object fuseLength,
    /* If true, the fuse is already lit when this component is added to the entity. */
    @JsonProperty("fuse_lit") @Nullable Boolean fuseLit,
    /* A scale factor applied to the knockback force caused by the explosion. */
    @JsonProperty("knockback_scaling") @Nullable Double knockbackScaling,
    /* A blocks explosion resistance will be capped at this value when an explosion occurs. */
    @JsonProperty("max_resistance") @Nullable Double maxResistance,
    /* Defines whether the explosion should apply fall damage negation to Players above the point of collision. */
    @JsonProperty("negates_fall_damage") @Nullable Boolean negatesFallDamage,
    /* The name of the particle effect to use. */
    @JsonProperty("particle_effect") @Nullable String particleEffect,
    /* The radius of the explosion in blocks and the amount of damage the explosion deals. */
    @JsonProperty("power") @Nullable Double power,
    /* The name of the sound effect played when the explosion triggers. */
    @JsonProperty("sound_effect") @Nullable String soundEffect,
    /* If true, the explosion will toggle blocks in the explosion radius. */
    @JsonProperty("toggles_blocks") @Nullable Boolean togglesBlocks
) {
}
