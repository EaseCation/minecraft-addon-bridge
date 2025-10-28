package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.processors;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BlockIgnore(
    @JsonProperty("blocks") List<String> blocks,
    @JsonProperty("processor_type") String processorType
) {
}
