package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.structure_sets;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StructureSet(
    /* The description of this structure set. */
    @JsonProperty("description") Description description,
    /* Describes where structures in the set spawn relative to one another. Currently, the only placement type supported is random_spread, which scatters structures randomly with a given separation and spacing. */
    @JsonProperty("placement") Placement placement,
    /* A weighted list of Jigsaw Structure identifiers. Structures will be randomly chosen from this set during world generation. */
    @JsonProperty("structures") List<Object> structures
) {
    
        /* The description of this structure set. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this structure set. */
            @JsonProperty("identifier") String identifier
        ) {
        }
    
        /* Describes where structures in the set spawn relative to one another. Currently, the only placement type supported is random_spread, which scatters structures randomly with a given separation and spacing. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Placement(
            /* Seed used for the random generator to provide a unique scatter pattern. This is used to prevent overlap in the case where multiple structure sets use the same placement values. */
            @JsonProperty("salt") Integer salt,
            /* Padding (in chunks) within each grid cell. Structures will not generate within the padded area. */
            @JsonProperty("separation") Integer separation,
            /* Grid cell size (in chunks) to use when generating the structure. Structures will attempt to generate at a random position within each cell. */
            @JsonProperty("spacing") Integer spacing,
            /* Randomness algorithm used when placing structures. */
            @JsonProperty("spread_type") @Nullable Object spreadType,
            @JsonProperty("type") String type
        ) {
        }
}
