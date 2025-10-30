package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* [EXPERIMENTAL BEHAVIOR] The entity will attempt to toss the items from its inventory to a nearby recently played noteblock. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GoAndGiveItemsToNoteblock(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Sets the time an entity should continue delivering items to a noteblock after hearing it. */
    @JsonProperty("listen_time") @Nullable Integer listenTime,
    /* Event(s) to run when this mob throws items. */
    @JsonProperty("on_item_throw") @Nullable Object onItemThrow,
    /* Sets the desired distance to be reached before throwing the items towards the block. */
    @JsonProperty("reach_block_distance") @Nullable Double reachBlockDistance,
    /* Sets the entity's speed when running toward the block. */
    @JsonProperty("run_speed") @Nullable Double runSpeed,
    /* Sets the throw force. */
    @JsonProperty("throw_force") @Nullable Double throwForce,
    /* Sound to play when this mob throws an item. */
    @JsonProperty("throw_sound") @Nullable String throwSound,
    /* Sets the vertical throw multiplier that is applied on top of the throw force in the vertical direction. */
    @JsonProperty("vertical_throw_mul") @Nullable Double verticalThrowMul
) {
}
