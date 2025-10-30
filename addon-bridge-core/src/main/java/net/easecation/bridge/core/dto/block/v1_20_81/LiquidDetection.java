package net.easecation.bridge.core.dto.block.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * The definitions for how a block behaves when detecting liquid. Only one rule definition is allowed per liquid type - if multiple are specified, the first will be used and the rest will be ignored.
 * Experimental toggles required: Upcoming Creator Features
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LiquidDetection(
    @JsonProperty("detection_rules") @Nullable List<DefinitionRule> detectionRules
) {
}
