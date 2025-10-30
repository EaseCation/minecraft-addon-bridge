package net.easecation.bridge.core.dto.item.v1_19_0;

import com.fasterxml.jackson.annotation.*;

/* Knockback Resistance Item. Component put on items that provide knockback resistance. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record KnockbackResistance(
    /* Amount of knockback resistance provided with the total maximum protection being 1.0 */
    @JsonProperty("protection") Double protection
) {
}
