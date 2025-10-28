package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* An object that describes an item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemDescriptor(
    @JsonProperty("item") @Nullable Object item,
    /* [UNDOCUMENTED] A Molang expression ran against item or block to match. */
    @JsonProperty("tags") @Nullable String tags,
    /* [UNDOCUMENTED] A tag to lookup item or block by. */
    @JsonProperty("item_tag") @Nullable String itemTag
) {
}
