package net.easecation.bridge.core.dto.block.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Describes the component that will trigger an even at a regular interval between two values. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Tick(
    /* Does the event loop. */
    @JsonProperty("looping") @Nullable Boolean looping,
    /* The Range between which the component will trigger his event. */
    @JsonProperty("interval_range") @Nullable List<Object> intervalRange
) {
}
