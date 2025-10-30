package net.easecation.bridge.core.dto.feature.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Feature type {@code minecraft:vegetation<i>patch</i>feature} has not yet been documented. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VegetationPatchFeature(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* UNDOCUMENTED. */
    @JsonProperty("replaceable_blocks") @Nullable List<String> replaceableBlocks,
    /* UNDOCUMENTED. */
    @JsonProperty("ground_block") @Nullable String groundBlock,
    /* UNDOCUMENTED. */
    @JsonProperty("vegetation_feature") @Nullable String vegetationFeature,
    /* UNDOCUMENTED. */
    @JsonProperty("surface") @Nullable String surface,
    /* UNDOCUMENTED. */
    @JsonProperty("depth") @Nullable Integer depth,
    /* UNDOCUMENTED. */
    @JsonProperty("extra_deep_block_chance") @Nullable Double extraDeepBlockChance,
    /* UNDOCUMENTED. */
    @JsonProperty("vertical_range") @Nullable Integer verticalRange,
    /* UNDOCUMENTED. */
    @JsonProperty("vegetation_chance") @Nullable Double vegetationChance,
    /* UNDOCUMENTED. */
    @JsonProperty("horizontal_radius") @Nullable Integer horizontalRadius,
    /* UNDOCUMENTED. */
    @JsonProperty("extra_edge_column_chance") @Nullable Double extraEdgeColumnChance,
    /* UNDOCUMENTED. */
    @JsonProperty("waterlogged") @Nullable Boolean waterlogged
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
