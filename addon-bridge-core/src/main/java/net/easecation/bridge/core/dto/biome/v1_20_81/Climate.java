package net.easecation.bridge.core.dto.biome.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Describes temperature, humidity, precipitation, etc.  Biomes without this component will have default values. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Climate(
    /* UNDOCUMENTED. */
    @JsonProperty("temperature") @Nullable Double temperature,
    /* UNDOCUMENTED. */
    @JsonProperty("downfall") @Nullable Double downfall,
    /* UNDOCUMENTED. */
    @JsonProperty("red_spores") @Nullable Double redSpores,
    /* UNDOCUMENTED. */
    @JsonProperty("blue_spores") @Nullable Double blueSpores,
    /* UNDOCUMENTED. */
    @JsonProperty("ash") @Nullable Double ash,
    /* UNDOCUMENTED. */
    @JsonProperty("white_ash") @Nullable Double whiteAsh,
    /* UNDOCUMENTED. */
    @JsonProperty("snow_accumulation") @Nullable List<Double> snowAccumulation
) {
}
