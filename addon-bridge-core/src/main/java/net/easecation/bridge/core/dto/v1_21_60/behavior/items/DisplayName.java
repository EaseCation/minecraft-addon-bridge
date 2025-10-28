package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* Display Name item component. Display Names display the name of an item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DisplayName(
    /* Set the display name for an item. */
    @JsonProperty("value") String value
) {
}
