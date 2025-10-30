package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* The icon item componenent determines the icon to represent the item in the UI and elsewhere. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Icon(
    /* Contains key-value pairs of textures used by the item */
    @JsonProperty("textures") Textures textures
) {
    
        /* Contains key-value pairs of textures used by the item */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Textures(
            /* The key from the resource<i>pack/textures/item</i>texture.json {@code texture<i>data} object associated with the texture file Example: blaze</i>powder. */
            @JsonProperty("default") String defaultField
        ) {
        }
}
