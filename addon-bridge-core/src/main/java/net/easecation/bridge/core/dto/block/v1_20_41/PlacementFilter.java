package net.easecation.bridge.core.dto.block.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* [Experimental] */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlacementFilter(
    /* List of conditions where the block can be placed/survive. Limited to 64 conditions. Each condition is a JSON Object that must contain at least one (and can contain both) of the parameters allowed<i>faces or block</i>filter as shown below. */
    @JsonProperty("conditions") @Nullable List<Object> conditions
) {
}
