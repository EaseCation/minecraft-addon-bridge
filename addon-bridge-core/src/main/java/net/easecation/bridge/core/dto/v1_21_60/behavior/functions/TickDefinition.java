package net.easecation.bridge.core.dto.v1_21_60.behavior.functions;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Mcfunction that are to be called per game tick (20 times per second). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TickDefinition(
    /* The collection of function path to execute. */
    @JsonProperty("values") @Nullable List<String> values
) {
}
