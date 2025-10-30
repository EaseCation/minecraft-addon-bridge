package net.easecation.bridge.core.dto.feature.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* {@code minecraft:hell<i>cave</i>carver<i>feature} carves a cave through the Nether in the current chunk, and in every chunk around the current chunk in an 8 radial pattern.This feature will also only work when placed specifically in the pass {@code pregeneration</i>pass}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HellCaveCarverFeature(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* Reference to the block to fill the cave with. */
    @JsonProperty("fill_with") @Nullable String fillWith,
    /* How many blocks to increase the cave radius by, from the center point of the cave. */
    @JsonProperty("width_modifier") @Nullable MolangNumber widthModifier
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
