package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* The damage component determines how much extra damage the item does on attack. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Damage(
    /* How much extra damage the item does, must be a positive number. */
    @JsonProperty("value") Double value
) {
}
