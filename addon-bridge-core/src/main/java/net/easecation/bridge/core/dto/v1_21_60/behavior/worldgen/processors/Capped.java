package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.processors;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Capped(
    @JsonProperty("delegate") Object delegate,
    @JsonProperty("limit") Object limit,
    @JsonProperty("processor_type") String processorType
) {
}
