package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface EntitiesBb {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntitiesBb_Variant0(
    ) implements EntitiesBb {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntitiesBb_Variant1(
    ) implements EntitiesBb {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntitiesBb_Variant2(
        @JsonProperty("item") @Nullable Object item
    ) implements EntitiesBb {
    }
}
