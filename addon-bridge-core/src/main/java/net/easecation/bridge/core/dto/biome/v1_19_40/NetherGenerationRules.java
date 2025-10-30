package net.easecation.bridge.core.dto.biome.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Controls how this biome is instantiated (and then potentially modified) during world generation of the nether. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NetherGenerationRules(
    /* Temperature with which this biome should selected, relative to other biomes. */
    @JsonProperty("target_temperature") @Nullable Double targetTemperature,
    /* Humidity with which this biome should selected, relative to other biomes. */
    @JsonProperty("target_humidity") @Nullable Double targetHumidity,
    /* Altitude with which this biome should selected, relative to other biomes. */
    @JsonProperty("target_altitude") @Nullable Double targetAltitude,
    /* Weirdness with which this biome should selected, relative to other biomes. */
    @JsonProperty("target_weirdness") @Nullable Double targetWeirdness,
    /* Weight with which this biome should selected, relative to other biomes. */
    @JsonProperty("weight") @Nullable Double weight
) {
}
