package net.easecation.bridge.core.dto.block.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* Contains information about where the player placed the block. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlacementPosition(
    /* Block states you wish to enable */
    @JsonProperty("enabled_states") List<String> enabledStates
) {
}
