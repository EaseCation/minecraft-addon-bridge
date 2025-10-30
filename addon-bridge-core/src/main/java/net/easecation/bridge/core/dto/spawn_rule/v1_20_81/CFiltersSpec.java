package net.easecation.bridge.core.dto.spawn_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface CFiltersSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CFiltersSpec_Variant0(
        /* All tests in an {@code all_of} group must pass in order for the group to pass. */
        @JsonProperty("all_of") @Nullable CGroupsSpec allOf,
        /* One or more tests in an {@code any_of} group must pass in order for the group to pass. */
        @JsonProperty("any_of") @Nullable CGroupsSpec anyOf,
        /* All tests in a {@code none_of} group must fail in order for the group to pass. */
        @JsonProperty("none_of") @Nullable CGroupsSpec noneOf
    ) implements CFiltersSpec {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CFiltersSpec_Variant1(
    ) implements CFiltersSpec {
    }
}
