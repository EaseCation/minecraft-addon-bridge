package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Specifies the maximum weight limit that a storage item can hold */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StorageWeightLimit(
    /* The maximum allowed weight of the sum of all contained items. */
    @JsonProperty("max_weight_limit") @Nullable Integer maxWeightLimit
) {
}
