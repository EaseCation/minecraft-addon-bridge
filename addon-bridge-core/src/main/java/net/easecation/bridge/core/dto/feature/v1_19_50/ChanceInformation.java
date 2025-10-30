package net.easecation.bridge.core.dto.feature.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ChanceInformation(
    /* UNDOCUMENTED. */
    @JsonProperty("numerator") @Nullable Double numerator,
    /* UNDOCUMENTED. */
    @JsonProperty("denominator") @Nullable Double denominator
) {
}
