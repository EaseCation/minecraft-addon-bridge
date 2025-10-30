package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines this entity's inventory properties. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Inventory(
    /* Number of slots that this entity can gain per extra strength. */
    @JsonProperty("additional_slots_per_strength") @Nullable Integer additionalSlotsPerStrength,
    /* If true, the contents of this inventory can be removed by a hopper. */
    @JsonProperty("can_be_siphoned_from") @Nullable Boolean canBeSiphonedFrom,
    /* Type of container this entity has. Can be horse, minecart<i>chest, chest</i>boat, minecart_hopper, inventory, container or hopper */
    @JsonProperty("container_type") @Nullable String containerType,
    /* Number of slots the container has. */
    @JsonProperty("inventory_size") @Nullable Integer inventorySize,
    /* If true, only the entity can access the inventory. */
    @JsonProperty("private") @Nullable Boolean privateField,
    /* If true, the entity's inventory can only be accessed by its owner or itself. */
    @JsonProperty("restrict_to_owner") @Nullable Boolean restrictToOwner
) {
}
