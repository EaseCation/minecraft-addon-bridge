package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Storage Items can be used by other components to store other items within this item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StorageItem(
    /* Determines whether another Storage Item is allowed inside of this item. Default is true. */
    @JsonProperty("allow_nested_storage_items") @Nullable Boolean allowNestedStorageItems,
    /* List of items that are exclusively allowed in this Storage Item. If empty all items are allowed. */
    @JsonProperty("allowed_items") @Nullable List<String> allowedItems,
    /* List of items that are not allowed in this Storage Item. */
    @JsonProperty("banned_items") @Nullable List<String> bannedItems,
    /* The maximum number of different item stacks. Maximum is 64. Default is 64. */
    @JsonProperty("max_slots") @Nullable Integer maxSlots
) {
}
