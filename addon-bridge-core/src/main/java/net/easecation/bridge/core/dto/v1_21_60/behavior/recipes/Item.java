package net.easecation.bridge.core.dto.v1_21_60.behavior.recipes;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Recipe item 1.12.0 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Item {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Item_ItemIdentifier(
        String value
    ) implements Item {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Item_Variant1(
        @JsonProperty("item") String item,
        @JsonProperty("data") @Nullable Integer data,
        @JsonProperty("count") @Nullable Integer count
    ) implements Item {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Item_Variant2(
        /* The item to unlock */
        @JsonProperty("tag") String tag
    ) implements Item {}
}
