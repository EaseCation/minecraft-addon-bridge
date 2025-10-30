package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The list of items that can be used to increase the entity's temper and speed up the taming process. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CgcFeedItems(
    /* Name of the item this entity likes and can be used to increase this entity's temper. */
    @JsonProperty("item") @Nullable EntityJ item,
    /* The amount of temper this entity gains when fed this item. */
    @JsonProperty("temper_mod") @Nullable Double temperMod
) {
}
