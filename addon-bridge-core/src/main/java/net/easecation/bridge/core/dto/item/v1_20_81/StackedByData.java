package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* The stacked by data component determines if the same item with different aux values can stack. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StackedByData(
    /* Also defines whether the item actors can merge while floating in the world. */
    @JsonProperty("value") Boolean value
) {
}
