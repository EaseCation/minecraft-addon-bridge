package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* The damage component determines how much extra damage the item does on attack. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Damage {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Damage_Variant0(
        Double value
    ) implements Damage {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Damage_Variant1(
        /* How much extra damage the item does, must be a positive number. */
        @JsonProperty("value") Double value
    ) implements Damage {}
}
