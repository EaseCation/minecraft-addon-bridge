package net.easecation.bridge.core.dto.item.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Wearable item component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Wearable(
    /* UNDOCUMENTED. */
    @JsonProperty("dispensable") @Nullable Boolean dispensable,
    /* equipment_slot: slot.weapon.mainhand, slot.weapon.offhand, slot.armor.head, slot.armor.chest, slot.armor.legs, slot.armor.feet, slot.hotbar, slot.inventory, slot.enderchest, slot.saddle, slot.armor, slot.chest */
    @JsonProperty("slot") @Nullable String slot
) {
}
