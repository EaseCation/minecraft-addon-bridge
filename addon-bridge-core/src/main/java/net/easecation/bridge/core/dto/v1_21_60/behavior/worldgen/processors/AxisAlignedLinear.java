package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.processors;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AxisAlignedLinear(
    @JsonProperty("axis") @Nullable String axis,
    @JsonProperty("max_chance") @Nullable Double maxChance,
    @JsonProperty("max_dist") @Nullable Integer maxDist,
    @JsonProperty("min_chance") @Nullable Double minChance,
    @JsonProperty("min_dist") @Nullable Integer minDist,
    @JsonProperty("predicate_type") String predicateType
) {
}
