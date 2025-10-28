package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The list of items that this entity dislikes and will cause it to get angry if used while untamed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CieAutoRejectItems(
    /* Name of the item this entity dislikes and will cause it to get angry if used while untamed. */
    @JsonProperty("item") @Nullable EntitiesBb item
) {
}
