package net.easecation.bridge.core.dto.item.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The on_use item component allows you to receive an event when the item is used. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnUse(
    /* Event trigger for when the item is used. */
    @JsonProperty("on_use") OnUseData onUse
) {
    
        /* Event trigger for when the item is used. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record OnUseData(
            /* UNDOCUMENTED. */
            @JsonProperty("event") @Nullable String event,
            /* UNDOCUMENTED. */
            @JsonProperty("target") @Nullable String target
        ) {
        }
}
