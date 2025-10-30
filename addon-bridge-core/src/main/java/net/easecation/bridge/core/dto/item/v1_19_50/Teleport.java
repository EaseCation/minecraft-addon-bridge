package net.easecation.bridge.core.dto.item.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Teleport(
    /* UNDOCUMENTED. */
    @JsonProperty("target") @Nullable String target,
    /* UNDOCUMENTED. */
    @JsonProperty("max_range") @Nullable List<Double> maxRange
) {
}
