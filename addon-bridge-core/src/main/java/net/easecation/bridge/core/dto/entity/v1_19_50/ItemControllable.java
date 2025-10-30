package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Efines what items can be used to control this entity while ridden. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemControllable(
    /* List of items that can be used to control this entity. */
    @JsonProperty("control_items") @Nullable Object controlItems
) {
}
