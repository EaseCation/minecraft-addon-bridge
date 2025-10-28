package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Contains information about the player's rotation when the block was placed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlacementDirection(
    /* Block states you wish to enable */
    @JsonProperty("enabled_states") List<String> enabledStates,
    /* This rotation offset only applies to the horizontal state values */
    @JsonProperty("y_rotation_offset") @Nullable Double yRotationOffset
) {
}
