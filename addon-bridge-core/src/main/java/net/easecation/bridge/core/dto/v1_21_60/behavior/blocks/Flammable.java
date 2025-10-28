package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Describes the flammable properties for this block. If set to true, default values are used. If set to false, or if this component is omitted, the block will not be able to catch on fire naturally from neighbors, but it can still be directly ignited. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Flammable {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Flammable_Variant0(
        Boolean value
    ) implements Flammable {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Flammable_Variant1(
        /* A modifier affecting the chance that this block will catch flame when next to a fire. Values are greater than or equal to 0, with a higher number meaning more likely to catch on fire */
        @JsonProperty("catch_chance_modifier") @Nullable Double catchChanceModifier,
        /* A modifier affecting the chance that this block will be destroyed by flames when on fire. */
        @JsonProperty("destroy_chance_modifier") @Nullable Double destroyChanceModifier
    ) implements Flammable {}
}
