package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Entities with this component will have a maximum auto step height that is different depending on wether they are on a block that prevents jumping. Incompatible with "runtime_identifier": "minecraft:horse". */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VariableMaxAutoStep(
    /* The maximum auto step height when on any other block. */
    @JsonProperty("base_value") @Nullable Double baseValue,
    /* The maximum auto step height when on any other block and controlled by the player. */
    @JsonProperty("controlled_value") @Nullable Double controlledValue,
    /* The maximum auto step height when on a block that prevents jumping. */
    @JsonProperty("jump_prevented_value") @Nullable Double jumpPreventedValue
) {
}
