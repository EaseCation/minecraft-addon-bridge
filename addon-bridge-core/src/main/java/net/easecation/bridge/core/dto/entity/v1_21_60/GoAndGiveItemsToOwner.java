package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* [EXPERIMENTAL BEHAVIOR] The entity will attempt to toss the items from its inventory to its owner. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GoAndGiveItemsToOwner(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Event(s) to run when this mob throws items. */
    @JsonProperty("on_item_throw") @Nullable Trigger onItemThrow,
    /* Sets the desired distance to be reached before giving items to owner. */
    @JsonProperty("reach_mob_distance") @Nullable Double reachMobDistance,
    /* Sets the entity's speed when running toward the owner. */
    @JsonProperty("run_speed") @Nullable Double runSpeed,
    /* Sets the throw force. */
    @JsonProperty("throw_force") @Nullable Double throwForce,
    /* Sound to play when this mob throws an item. */
    @JsonProperty("throw_sound") @Nullable String throwSound,
    /* Sets the vertical throw multiplier that is applied on top of the throw force in the vertical direction. */
    @JsonProperty("vertical_throw_mul") @Nullable Double verticalThrowMul
) {
}
