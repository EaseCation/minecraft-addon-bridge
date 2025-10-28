package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Describes temperature, humidity, precipitation, etc.  Biomes without this component will have default values. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Climate(
    /* Density of ash precipitation visuals. */
    @JsonProperty("ash") @Nullable Double ash,
    /* Density of blue spore precipitation visuals. */
    @JsonProperty("blue_spores") @Nullable Double blueSpores,
    /* Amount that precipitation affects colors and block changes. */
    @JsonProperty("downfall") @Nullable Double downfall,
    /* Density of blue spore precipitation visuals. */
    @JsonProperty("red_spores") @Nullable Double redSpores,
    /* Minimum and maximum snow level, each multiple of 0.125 is another snow layer. */
    @JsonProperty("snow_accumulation") @Nullable List<Double> snowAccumulation,
    /* Temperature affects a variety of visual and behavioral things, including snow and ice placement, sponge drying, and sky color. */
    @JsonProperty("temperature") @Nullable Double temperature,
    /* Density of white ash precipitation visuals. */
    @JsonProperty("white_ash") @Nullable Double whiteAsh
) {
}
