package net.easecation.bridge.core.dto.item.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* Fuel component. Allows this item to be used as fuel in a furnace to {@code cook} other items. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Fuel(
    /* How long in seconds will this fuel cook items for. */
    @JsonProperty("duration") Double duration
) {
}
