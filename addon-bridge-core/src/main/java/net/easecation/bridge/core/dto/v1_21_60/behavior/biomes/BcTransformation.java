package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BcTransformation {
    /* String name of a Biome. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BcTransformation_Biome(
        /* String name of a Biome. */
        String value
    ) implements BcTransformation {}
    /* Array of any size. If an array, each entry can be a Biome name string, or an array of size 2, where the first entry is a Biome name and the second entry is a positive integer representing how that Biome is weighted against other entries. If no weight is provided, a weight of 1 is used.. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BcTransformation_BlockReference(
    ) implements BcTransformation {}
}
