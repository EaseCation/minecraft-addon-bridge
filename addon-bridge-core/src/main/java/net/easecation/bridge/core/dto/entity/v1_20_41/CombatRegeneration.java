package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Gives Regeneration I and removes Mining Fatigue from the mob that kills the Actor`s attack target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CombatRegeneration(
    /* Determines if the mob will grant mobs of the same type combat buffs if they kill the target. */
    @JsonProperty("apply_to_family") @Nullable Boolean applyToFamily,
    /* Determines if the mob will grant itself the combat buffs if it kills the target. */
    @JsonProperty("apply_to_self") @Nullable Boolean applyToSelf,
    /* The duration in seconds of Regeneration I added to the mob. */
    @JsonProperty("regeneration_duration") @Nullable Integer regenerationDuration
) {
}
