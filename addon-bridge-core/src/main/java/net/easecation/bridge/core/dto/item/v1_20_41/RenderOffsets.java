package net.easecation.bridge.core.dto.item.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Render offsets component: optional values can be given to offset the way the item is rendered. Depreciated in json formats 1.20.10 and higher */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RenderOffsets(
    /* Right hand transform data. */
    @JsonProperty("main_hand") @Nullable String mainHand,
    /* Left hand transform data. */
    @JsonProperty("off_hand") @Nullable String offHand
) {
}
