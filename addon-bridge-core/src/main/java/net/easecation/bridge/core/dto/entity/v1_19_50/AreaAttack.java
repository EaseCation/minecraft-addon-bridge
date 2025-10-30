package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A component that does damage to entities that get within range. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AreaAttack(
    /* How much damage per tick is applied to entities that enter the damage range. */
    @JsonProperty("damage_per_tick") @Nullable Integer damagePerTick,
    /* How close a hostile entity must be to have the damage applied. */
    @JsonProperty("damage_range") @Nullable Double damageRange,
    /* Filter to see which entities can be affected by the attack. */
    @JsonProperty("entity_filter") @Nullable Filters entityFilter,
    /* what causes the attack to occur. */
    @JsonProperty("cause") @Nullable String cause
) {
}
