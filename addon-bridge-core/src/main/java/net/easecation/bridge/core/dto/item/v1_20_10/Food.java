package net.easecation.bridge.core.dto.item.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* When an item has a food component, it becomes edible to the player. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Food(
    /* If true you can always eat this item (even when not hungry), defaults to false. */
    @JsonProperty("can_always_eat") @Nullable Boolean canAlwaysEat,
    /* How much nutrition does this food item give the player when eaten. */
    @JsonProperty("nutrition") @Nullable Double nutrition,
    /* UNDOCUMENTED. */
    @JsonProperty("on_consume") @Nullable OnConsume onConsume,
    /* If true, this food item is considered meat. */
    @JsonProperty("is_meat") @Nullable Boolean isMeat
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record OnConsume(
            /* UNDOCUMENTED. */
            @JsonProperty("event") @Nullable String event,
            /* UNDOCUMENTED. */
            @JsonProperty("target") @Nullable String target
        ) {
        }
}
