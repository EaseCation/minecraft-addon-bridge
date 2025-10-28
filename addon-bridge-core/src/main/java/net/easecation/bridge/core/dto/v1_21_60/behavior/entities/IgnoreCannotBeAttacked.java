package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* When set, allows the entity to attack entities that have the "minecraft:cannot<i>be</i>attacked" component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IgnoreCannotBeAttacked(
    /* Defines which entities are exceptions and are allowed to be attacked by the owner entity, potentially attacked entity is subject "other". If this is not specified then all attacks by the owner are allowed. */
    @JsonProperty("filters") @Nullable Filters filters
) {
}
