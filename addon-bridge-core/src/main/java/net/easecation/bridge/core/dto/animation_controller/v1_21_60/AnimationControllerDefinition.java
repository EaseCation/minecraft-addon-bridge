package net.easecation.bridge.core.dto.animation_controller.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;

/* Animation controller for behaviors. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AnimationControllerDefinition(
    @JsonProperty("format_version") String formatVersion,
    /* The animation controllers schema for. */
    @JsonProperty("animation_controllers") Map<String, Object> animationControllers
) {
}
