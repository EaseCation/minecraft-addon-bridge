package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines sets of items that can be used to trigger events when used on this entity. The item will also be taken and placed in the entity's inventory. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Giveable(
    /* Defines sets of items that can be used to trigger events when used on this entity. The item will also be taken and placed in the entity's inventory. */
    @JsonProperty("triggers") @Nullable Triggers triggers
) {
    
        /* Defines sets of items that can be used to trigger events when used on this entity. The item will also be taken and placed in the entity's inventory. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Triggers(
            /* An optional cool down in seconds to prevent spamming interactions. */
            @JsonProperty("cooldown") @Nullable Double cooldown,
            /* The list of items that can be given to the entity to place in their inventory. */
            @JsonProperty("items") @Nullable List<EntityBb> items,
            /* Event to fire when the correct item is given. */
            @JsonProperty("on_give") @Nullable Event onGive
        ) {
        }
}
