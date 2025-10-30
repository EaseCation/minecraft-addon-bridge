package net.easecation.bridge.core.dto.animation.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;

/* Animation for behavior for. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AnimationsDefinition(
    @JsonProperty("format_version") String formatVersion,
    /* The animation specification. */
    @JsonProperty("animations") Map<String, Object> animations
) {
}
