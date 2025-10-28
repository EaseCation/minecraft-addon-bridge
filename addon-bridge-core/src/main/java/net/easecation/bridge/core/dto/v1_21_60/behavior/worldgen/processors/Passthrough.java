package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.processors;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Passthrough(
    @JsonProperty("type") String type
) {
}
