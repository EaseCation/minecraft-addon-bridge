package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows a mob to join and migrate between villages and other dwellings. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dweller(
    /* The type of dwelling the mob wishes to join. Current Types: village */
    @JsonProperty("dwelling_type") @Nullable String dwellingType,
    /* The role of which the mob plays in the dwelling. Current Roles: inhabitant, defender, hostile, passive. */
    @JsonProperty("dweller_role") @Nullable String dwellerRole,
    /* How often the mob checks on their dwelling status in ticks. Positive values only. */
    @JsonProperty("update_interval_base") @Nullable Double updateIntervalBase,
    /* The variant value in ticks that will be added to the update<i>interval</i>base. */
    @JsonProperty("update_interval_variant") @Nullable Double updateIntervalVariant,
    /* Whether or not the mob can find and add POI's to the dwelling. */
    @JsonProperty("can_find_poi") @Nullable Boolean canFindPoi,
    /* How much reputation should the players be rewarded on first founding?. */
    @JsonProperty("first_founding_reward") @Nullable Integer firstFoundingReward,
    /* Can this mob migrate between dwellings? Or does it only have its initial dwelling?. */
    @JsonProperty("can_migrate") @Nullable Boolean canMigrate,
    /* A padding distance for checking if the mob is within the dwelling. */
    @JsonProperty("dwelling_bounds_tolerance") @Nullable Double dwellingBoundsTolerance,
    /* Allows the user to define a starting profession for this particular Dweller, instead of letting them choose organically. (They still need to gain experience from trading before this takes effect.) */
    @JsonProperty("preferred_profession") @Nullable String preferredProfession
) {
}
