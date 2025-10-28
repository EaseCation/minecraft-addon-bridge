package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Adds a timer since last rested to see if phantoms should spawn. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Insomnia(
    /* Number of days the mob has to stay up until the insomnia effect begins. */
    @JsonProperty("days_until_insomnia") @Nullable Double daysUntilInsomnia
) {
}
