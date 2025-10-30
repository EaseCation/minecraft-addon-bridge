package net.easecation.bridge.core.dto.item.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Render offsets component: optional values can be given to offset the way the item is rendered. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RenderOffsets(
    /* Right hand transform data. */
    @JsonProperty("main_hand") @Nullable String mainHand,
    /* Left hand transform data. */
    @JsonProperty("off_hand") @Nullable String offHand
) {
}
