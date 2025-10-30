package net.easecation.bridge.core.dto.feature.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Coordinate {
    /* Expression for the coordinate (evaluated each iteration). Mutually exclusive with random distribution object below. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Coordinate_Variant0(
    ) implements Coordinate {
    }
    /* Distribution for the coordinate (evaluated each iteration). Mutually exclusive with Molang expression above. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Coordinate_Variant1(
        /* Type of distribution - uniform random, gaussian (centered in the range), or grid (either fixed-step or jittered). */
        @JsonProperty("distribution") String distribution,
        /* When the distribution type is grid, defines the distance between steps along this axis. */
        @JsonProperty("step_size") @Nullable Integer stepSize,
        /* When the distribution type is grid, defines the offset along this axis. */
        @JsonProperty("grid_offset") @Nullable Integer gridOffset,
        /* UNDOCUMENTED. */
        @JsonProperty("extent") List<MolangNumber> extent
    ) implements Coordinate {
    }
}
