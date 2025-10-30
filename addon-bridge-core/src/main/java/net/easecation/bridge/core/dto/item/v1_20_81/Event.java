package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Event(
    @JsonProperty("add_mob_effect") @Nullable AddMobEffect addMobEffect,
    @JsonProperty("damage") @Nullable Shoot damage,
    @JsonProperty("decrement_stack") @Nullable net.easecation.bridge.core.dto.EmptyObject decrementStack,
    @JsonProperty("remove_mob_effect") @Nullable RemoveMobEffect removeMobEffect,
    @JsonProperty("shoot") @Nullable Shoot shoot,
    @JsonProperty("swing") @Nullable net.easecation.bridge.core.dto.EmptyObject swing,
    @JsonProperty("teleport") @Nullable Teleport teleport,
    @JsonProperty("transform_item") @Nullable TransformItem transformItem
) {
}
