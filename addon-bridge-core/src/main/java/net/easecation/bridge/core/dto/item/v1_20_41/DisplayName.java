package net.easecation.bridge.core.dto.item.v1_20_41;

import com.fasterxml.jackson.annotation.*;

/* Display Name item component. Display Names display the name of an item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DisplayName(
    /* Set the display name for an item. */
    @JsonProperty("value") String value
) {
}
