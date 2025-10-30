package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerExperience(
    /* UNDOCUMENTED. */
    @JsonProperty("value") @Nullable Double value,
    /* UNDOCUMENTED. */
    @JsonProperty("max") @Nullable Double max
) {
}
