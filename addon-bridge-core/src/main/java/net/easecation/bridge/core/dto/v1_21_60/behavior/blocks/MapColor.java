package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Sets the color of the block when rendered to a map. The color is represented as a hex value in the format "#RRGGBB". May also be expressed as an array of [R, G, B] from 0 to 255. If this component is omitted, the block will not show up on the map. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface MapColor {
    @JsonIgnoreProperties(ignoreUnknown = true) @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION) 
    record MapColor_Variant0(
    ) implements MapColor {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record MapColor_Variant1(
        @JsonProperty("color") BiColor color,
        /* Tint multiplied to the color. Tint method logic varies, but often refers to the "rain" and "temperature" of the biome the block is placed in to compute the tint. */
        @JsonProperty("tint_method") @Nullable TintMethod tintMethod
    ) implements MapColor {
    }
}
