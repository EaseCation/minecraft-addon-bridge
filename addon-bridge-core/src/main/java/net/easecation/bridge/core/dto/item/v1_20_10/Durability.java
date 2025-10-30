package net.easecation.bridge.core.dto.item.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Durability item component: how much damage can this item take before breaking. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Durability(
    /* Damange chance. */
    @JsonProperty("damage_chance") DamageChance damageChance,
    /* Maximum durability is the amount of damage that this item can take before breaking. */
    @JsonProperty("max_durability") @Nullable Double maxDurability
) {
    
        /* Damange chance. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record DamageChance(
            /* The minimum. */
            @JsonProperty("min") Integer min,
            /* The minimum. */
            @JsonProperty("max") Integer max
        ) {
        }
}
