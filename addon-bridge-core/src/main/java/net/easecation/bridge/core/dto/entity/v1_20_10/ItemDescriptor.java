package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* An object that describes an item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemDescriptor(
    /* [UNDOCUMENTED] A Molang expression ran against item or block to match. */
    @JsonProperty("tags") @Nullable String tags,
    /* [UNDOCUMENTED] A tag to lookup item or block by. */
    @JsonProperty("item_tag") @Nullable String itemTag
) {
}
