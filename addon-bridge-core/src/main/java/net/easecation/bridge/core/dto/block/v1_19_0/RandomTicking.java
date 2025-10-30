package net.easecation.bridge.core.dto.block.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Describes the component that will trigger an even at a regular interval between two values. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomTicking(
    /* Describes the component that will trigger an even at a regular interval between two values. */
    @JsonProperty("on_tick") @Nullable String onTick,
    /* Does the event loop. */
    @JsonProperty("looping") @Nullable Boolean looping,
    /* The Range between which the component will trigger his event. */
    @JsonProperty("range") @Nullable List<Object> range
) {
}
