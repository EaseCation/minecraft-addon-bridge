package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The enchantable component determines what enchantments can be applied to the item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Enchantable(
    /* If true you can always eat this item (even when not hungry), defaults to false. */
    @JsonProperty("slot") String slot,
    /* The value of the enchantment. */
    @JsonProperty("value") @Nullable Double value
) {
}
