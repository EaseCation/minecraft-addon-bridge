package net.easecation.bridge.core.dto.feature.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FeaturesDefinition(
    @JsonProperty("format_version") @Nullable String formatVersion
) {
}
