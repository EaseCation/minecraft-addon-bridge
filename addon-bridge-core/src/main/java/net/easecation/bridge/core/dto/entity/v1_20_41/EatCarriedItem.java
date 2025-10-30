package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* If the mob is carrying a food item, the mob will eat it and the effects will be applied to the mob. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EatCarriedItem(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Time in seconds the mob should wait before eating the item. */
    @JsonProperty("delay_before_eating") @Nullable Double delayBeforeEating
) {
}
