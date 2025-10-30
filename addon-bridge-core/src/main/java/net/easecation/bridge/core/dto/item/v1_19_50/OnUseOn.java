package net.easecation.bridge.core.dto.item.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The on<i>use</i>on item component allows you to receive an event when the item is used on a block in the world. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnUseOn(
    /* Event trigger for when the item is used. */
    @JsonProperty("on_use") @Nullable OnUse onUse
) {
    
        /* Event trigger for when the item is used. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record OnUse(
            /* UNDOCUMENTED. */
            @JsonProperty("event") @Nullable String event,
            /* UNDOCUMENTED. */
            @JsonProperty("target") @Nullable String target
        ) {
        }
}
