package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

/* Tint multiplied to the color. */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TintMethod {
    @JsonProperty("none") NONE,
    @JsonProperty("default_foliage") DEFAULT_FOLIAGE,
    @JsonProperty("birch_foliage") BIRCH_FOLIAGE,
    @JsonProperty("evergreen_foliage") EVERGREEN_FOLIAGE,
    @JsonProperty("dry_foliage") DRY_FOLIAGE,
    @JsonProperty("grass") GRASS,
    @JsonProperty("water") WATER
}
