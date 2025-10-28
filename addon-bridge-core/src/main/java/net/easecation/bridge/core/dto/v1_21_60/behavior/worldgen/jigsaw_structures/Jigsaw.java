package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Jigsaw(
    /* Specifies the biomes in which the Jigsaw Structure can generate. */
    @JsonProperty("biome_filters") @Nullable Filters biomeFilters,
    /* The description of this jigsaw. */
    @JsonProperty("description") Description description,
    /* Heightmap used to calculate the relative start height. For example, a heightmap<i>projection of ocean</i>floor and a start_height of 10 means the Jigsaw Structure will begin generating 10 blocks above the ocean floor. */
    @JsonProperty("heightmap_projection") @Nullable String heightmapProjection,
    /* The maximum recursion depth for Jigsaw Structure Generation. For example, a Jigsaw Structure that builds a road with a max_depth of 5 will only have paths that are a maximum of 5 structures templates in length in any given direction from the origin. */
    @JsonProperty("max_depth") Integer maxDepth,
    /* World height at which the Jigsaw Structure should begin generation. */
    @JsonProperty("start_height") Integer startHeight,
    /* The first Template Pool to use when generating the Jigsaw Structure. */
    @JsonProperty("start_pool") String startPool,
    /* Specifies the world generation phase in which the structure is generated. This is used as a grouping concept to keep similar world-generation features generally bundled together. */
    @JsonProperty("step") String step,
    /* How the terrain should adapt relative to the generated Jigsaw Structure. */
    @JsonProperty("terrain_adaptation") @Nullable String terrainAdaptation
) {
    
        /* The description of this jigsaw. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* Identifier of the Jigsaw Structure. This is used with commands such as /locate, as well as within Structure Set definitions to specify which Jigsaw Structures are included in a given set. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
