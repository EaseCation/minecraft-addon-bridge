package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The minecraft entity behavior specification. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntitiesDefinition(
    /* Allows an entity to use experimental features. */
    @JsonProperty("use_beta_features") @Nullable Boolean useBetaFeatures,
    @JsonProperty("format_version") FormatVersion formatVersion,
    @JsonProperty("minecraft:entity") Entity minecraft_entity
) {
}
