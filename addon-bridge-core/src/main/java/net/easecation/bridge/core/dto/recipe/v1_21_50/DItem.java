package net.easecation.bridge.core.dto.recipe.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface DItem {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record DItem_Variant0(
        /* The item to unlock */
        @JsonProperty("item") String item,
        /* The data of the item to unlock */
        @JsonProperty("data") @Nullable Integer data
    ) implements DItem {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record DItem_Variant1(
        /* The item to unlock */
        @JsonProperty("tag") String tag
    ) implements DItem {
    }
}
