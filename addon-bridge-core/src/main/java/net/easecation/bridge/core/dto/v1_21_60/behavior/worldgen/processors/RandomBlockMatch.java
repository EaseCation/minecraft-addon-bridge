package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.processors;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomBlockMatch(
    @JsonProperty("block") String block,
    @JsonProperty("predicate_type") String predicateType,
    @JsonProperty("probability") Double probability
) {
}
