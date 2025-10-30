package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to look at a player that is holding a tradable item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TradeInterest(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The Maximum time in seconds that the trader will hold an item before attempting to switch for a different item that takes the same trade. */
    @JsonProperty("carried_item_switch_time") @Nullable Double carriedItemSwitchTime,
    /* The time in seconds before the trader can use this goal again. */
    @JsonProperty("cooldown") @Nullable Double cooldown,
    /* The Maximum time in seconds that the trader will be interested with showing it's trade items. */
    @JsonProperty("interest_time") @Nullable Double interestTime,
    /* The Maximum time in seconds that the trader will wait when you no longer have items to trade. */
    @JsonProperty("remove_item_time") @Nullable Double removeItemTime,
    /* Distance in blocks this mob can be interested by a player holding an item they like. */
    @JsonProperty("within_radius") @Nullable Double withinRadius
) {
}
