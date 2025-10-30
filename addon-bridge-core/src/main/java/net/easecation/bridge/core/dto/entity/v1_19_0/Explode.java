package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines how the entity explodes. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Explode(
    /* If true, the explosion will destroy blocks in the explosion radius. */
    @JsonProperty("breaks_blocks") @Nullable Boolean breaksBlocks,
    /* If true, blocks in the explosion radius will be set on fire. */
    @JsonProperty("causes_fire") @Nullable Boolean causesFire,
    /* If true, whether the explosion breaks blocks is affected by the mob griefing game rule. */
    @JsonProperty("destroy_affected_by_griefing") @Nullable Boolean destroyAffectedByGriefing,
    /* If true, whether the explosion causes fire is affected by the mob griefing game rule. */
    @JsonProperty("fire_affected_by_griefing") @Nullable Boolean fireAffectedByGriefing,
    /* The range for the random amount of time the fuse will be lit before exploding, a negative value means the explosion will be immediate. */
    @JsonProperty("fuse_length") @Nullable Object fuseLength,
    /* If true, the fuse is already lit when this component is added to the entity. */
    @JsonProperty("fuse_lit") @Nullable Boolean fuseLit,
    /* A blocks explosion resistance will be capped at this value when an explosion occurs. */
    @JsonProperty("max_resistance") @Nullable Double maxResistance,
    /* The radius of the explosion in blocks and the amount of damage the explosion deals. */
    @JsonProperty("power") @Nullable Double power
) {
}
