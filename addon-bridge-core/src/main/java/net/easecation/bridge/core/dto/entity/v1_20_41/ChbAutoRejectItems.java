package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The list of items that this entity dislikes and will cause it to get angry if used while untamed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ChbAutoRejectItems(
    /* Name of the item this entity dislikes and will cause it to get angry if used while untamed. */
    @JsonProperty("item") @Nullable EntityJ item
) {
}
