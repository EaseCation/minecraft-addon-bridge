package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* The glint component determines whether the item has the enchanted glint render effect on it */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Glint {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Glint_Variant0(
        Boolean value
    ) implements Glint {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Glint_Variant1(
        /* Whether the item has the glint effect. */
        @JsonProperty("value") Boolean value
    ) implements Glint {}
}
