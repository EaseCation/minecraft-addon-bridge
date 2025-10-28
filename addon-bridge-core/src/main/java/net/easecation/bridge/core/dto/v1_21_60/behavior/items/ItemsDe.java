package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface ItemsDe {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record ItemsDe_Variant0(
    ) implements ItemsDe {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record ItemsDe_Variant1(
    ) implements ItemsDe {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record ItemsDe_Variant2(
        @JsonProperty("item") @Nullable Object item
    ) implements ItemsDe {
    }
}
