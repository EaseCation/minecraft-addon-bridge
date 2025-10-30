package net.easecation.bridge.core.dto.volume.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VolumesDefinition(
    @JsonProperty("format_version") @Nullable String formatVersion
) {
}
