package net.easecation.bridge.core.dto.camera.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* A camera preset is a set of values that define a camera's position and rotation. This can be used to quickly set a camera to a specific position and rotation. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CamerasDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:camera_preset") Object minecraft_cameraPreset
) {
}
