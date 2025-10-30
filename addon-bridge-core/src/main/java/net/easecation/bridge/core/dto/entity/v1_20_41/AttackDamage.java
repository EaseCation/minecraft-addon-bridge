package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;

/* Specifies how much damage is dealt by the entity when it attacks. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AttackDamage(
    /* How much an attack should damage a target. */
    @JsonProperty("value") Integer value
) {
}
