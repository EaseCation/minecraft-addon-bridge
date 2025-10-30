package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BfbEffect(
    /* UNDOCUMENTED. */
    @JsonProperty("name") @Nullable String name,
    /* The duration of the effect. */
    @JsonProperty("duration") @Nullable Integer duration,
    /* The amplifier of the effect. */
    @JsonProperty("amplifier") @Nullable Integer amplifier
) {
}
