package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Specifies the maximum weight limit that a storage item can hold. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StorageWeightModifier(
    /* The weight of this item when inside another Storage Item. 0 means item is not allowed in another Storage Item. */
    @JsonProperty("weight_in_storage_item") @Nullable Integer weightInStorageItem
) {
}
