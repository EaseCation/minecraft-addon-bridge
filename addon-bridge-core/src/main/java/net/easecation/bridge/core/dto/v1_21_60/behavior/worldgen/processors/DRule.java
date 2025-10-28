package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.processors;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DRule(
    @JsonProperty("block_entity_modifier") @Nullable Object blockEntityModifier,
    @JsonProperty("input_predicate") @Nullable Object inputPredicate,
    @JsonProperty("location_predicate") @Nullable Object locationPredicate,
    @JsonProperty("output_state") DBlockSpecifier outputState,
    @JsonProperty("position_predicate") @Nullable Object positionPredicate
) {
}
