package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BbFiltersSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BbFiltersSpec_Variant0(
        /* All tests in an {@code all_of} group must pass in order for the group to pass. */
        @JsonProperty("all_of") @Nullable BbGroupsSpec allOf,
        /* One or more tests in an {@code any_of} group must pass in order for the group to pass. */
        @JsonProperty("any_of") @Nullable BbGroupsSpec anyOf,
        /* All tests in a {@code none_of} group must fail in order for the group to pass. */
        @JsonProperty("none_of") @Nullable BbGroupsSpec noneOf
    ) implements BbFiltersSpec {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BbFiltersSpec_Variant1(
    ) implements BbFiltersSpec {
    }
}
