package net.easecation.bridge.core.dto.item.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AddMobEffect(
    /* UNDOCUMENTED. */
    @JsonProperty("effect") @Nullable String effect,
    /* UNDOCUMENTED. */
    @JsonProperty("target") @Nullable String target,
    /* UNDOCUMENTED. */
    @JsonProperty("duration") @Nullable Double duration,
    /* UNDOCUMENTED. */
    @JsonProperty("amplifier") @Nullable Double amplifier
) {
}
