package net.easecation.bridge.core.dto.item.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Weapon Item Component. Added to every weapon item such as axe, sword, trident, bow, crossbow. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Weapon(
    /* Trigger for letting you know when this item is used to hit a block. */
    @JsonProperty("on_hit_block") @Nullable net.easecation.bridge.core.dto.EmptyObject onHitBlock,
    /* Trigger for letting you know when this item is used to hurt another mob. */
    @JsonProperty("on_hurt_entity") @Nullable net.easecation.bridge.core.dto.EmptyObject onHurtEntity,
    /* Trigger for letting you know when this item hit another actor, but didn't do damage. */
    @JsonProperty("on_not_hurt_entity") @Nullable net.easecation.bridge.core.dto.EmptyObject onNotHurtEntity
) {
}
