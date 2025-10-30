package net.easecation.bridge.core.dto.block.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* [Experimental] */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlacementFilter(
    @JsonProperty("conditions") @Nullable List<Object> conditions
) {
}
