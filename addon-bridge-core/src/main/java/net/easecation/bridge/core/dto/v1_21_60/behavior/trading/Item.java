package net.easecation.bridge.core.dto.v1_21_60.behavior.trading;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Item {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Item_Variant0(
        String value
    ) implements Item {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Item_Variant1(
        @JsonProperty("item") @Nullable String item,
        @JsonProperty("price_multiplier") @Nullable Double priceMultiplier,
        @JsonProperty("functions") @Nullable Functions functions,
        /* UNDOCUMENTED. */
        @JsonProperty("biomes") @Nullable List<String> biomes,
        @JsonProperty("quantity") @Nullable Object quantity
    ) implements Item {}
}
