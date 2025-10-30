package net.easecation.bridge.core.dto.block.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Sets rules for under what conditions the block can be placed/survive. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlacementFilter(
    /* List of conditions where the block can be placed/survive. */
    @JsonProperty("conditions") @Nullable List<Object> conditions
) {
}
