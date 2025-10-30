package net.easecation.bridge.core.dto.item.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The icon item componenent determines the icon to represent the item in the UI and elsewhere. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Icon(
    /* An index or expression for which frame of the icon to display. Default resolves to 0. */
    @JsonProperty("frame") @Nullable String frame,
    /* Legacy texture id for older item icons. */
    @JsonProperty("legacy_id") @Nullable String legacyId,
    /* The key from the resource<i>pack/textures/item</i>texture.json {@code texture<i>data} object associated with the texture file Example: blaze</i>powder. */
    @JsonProperty("texture") String texture
) {
}
