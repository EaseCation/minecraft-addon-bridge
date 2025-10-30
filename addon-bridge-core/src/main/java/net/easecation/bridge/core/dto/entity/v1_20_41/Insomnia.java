package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Adds a timer since last rested to see if phantoms should spawn. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Insomnia(
    /* Number of days the mob has to stay up until the insomnia effect begins. */
    @JsonProperty("days_until_insomnia") @Nullable Double daysUntilInsomnia
) {
}
