package net.easecation.bridge.core.dto.item.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Adds bundle-specific interactions and tooltip to the item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BundleInteraction(
    /* The maximum number of slots in the bundle viewable by the plater. Can be from 1 to 64. Default is 12. */
    @JsonProperty("num_viewable_slots") @Nullable Integer numViewableSlots
) {
}
