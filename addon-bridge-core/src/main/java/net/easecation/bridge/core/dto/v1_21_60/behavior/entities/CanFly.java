package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Marks the entity as being able to fly, the pathfinder won't be restricted to paths where a solid block is required underneath it. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface CanFly {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CanFly_Variant1(
        Boolean value
    ) implements CanFly {}
}
