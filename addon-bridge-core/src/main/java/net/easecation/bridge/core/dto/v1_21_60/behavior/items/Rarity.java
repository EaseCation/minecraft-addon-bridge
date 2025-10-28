package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Specifies the base rarity and subsequently color of the item name when the player hovers the cursor over the item. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Rarity {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Rarity_Variant0(
        DcRarities value
    ) implements Rarity {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Rarity_Variant1(
        /* Sets the base rarity of the item. The rarity of an item automatically increases when enchanted, either to Rare when the base rarity is Common or Uncommon, or Epic when the base rarity is Rare. */
        @JsonProperty("value") @Nullable DcRarities value
    ) implements Rarity {}
}
