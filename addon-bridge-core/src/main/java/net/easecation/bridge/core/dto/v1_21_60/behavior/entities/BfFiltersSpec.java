package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BfFiltersSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BfFiltersSpec_Variant0(
        /* All tests in an {@code all_of} group must pass in order for the group to pass. */
        @JsonProperty("all_of") @Nullable BfGroupsSpec allOf,
        /* One or more tests in an {@code any_of} group must pass in order for the group to pass. */
        @JsonProperty("any_of") @Nullable BfGroupsSpec anyOf,
        /* All tests in a {@code none_of} group must fail in order for the group to pass. */
        @JsonProperty("none_of") @Nullable BfGroupsSpec noneOf
    ) implements BfFiltersSpec {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BfFiltersSpec_Variant1(
    ) implements BfFiltersSpec {}
}
