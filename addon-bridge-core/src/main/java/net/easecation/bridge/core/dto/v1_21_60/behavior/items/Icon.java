package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;

/* The icon item componenent determines the icon to represent the item in the UI and elsewhere. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Icon {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Icon_Variant0(
        String value
    ) implements Icon {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Icon_Variant1(
        /* Contains key-value pairs of textures used by the item */
        @JsonProperty("textures") Map<String, String> textures
    ) implements Icon {}
}
