package net.easecation.bridge.core.dto.feature.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* 'minecraft:fossil_feature' generates a skeletal structure composed of bone blocks and parametric ore blocks. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FossilFeature(
    @JsonProperty("description") Description description,
    /* Reference to the block to fill the cave with. */
    @JsonProperty("ore_block") @Nullable String oreBlock,
    /* UNDOCUMENTED */
    @JsonProperty("max_empty_corners") @Nullable Integer maxEmptyCorners
) {
}
