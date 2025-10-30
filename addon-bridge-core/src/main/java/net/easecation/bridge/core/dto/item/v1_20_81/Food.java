package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* When an item has a food component, it becomes edible to the player. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Food(
    /* If true you can always eat this item (even when not hungry), defaults to false. */
    @JsonProperty("can_always_eat") @Nullable Boolean canAlwaysEat,
    /* How much nutrition does this food item give the player when eaten. */
    @JsonProperty("nutrition") @Nullable Double nutrition,
    /* Saturation Modifier is used in this formula: (nutrition <i> saturation_modifier </i> 2) when appling the saturation buff. Which happens when you eat the item. */
    @JsonProperty("saturation_modifier") @Nullable Double saturationModifier,
    /* When used, convert the <i>this</i> item to the one specified by {@code using<i>converts</i>to}. */
    @JsonProperty("using_converts_to") @Nullable String usingConvertsTo
) {
}
