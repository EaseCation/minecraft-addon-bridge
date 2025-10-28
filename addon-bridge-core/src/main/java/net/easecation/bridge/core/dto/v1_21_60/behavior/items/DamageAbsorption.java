package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* It allows an item to absorb damage that would otherwise be dealt to its wearer. For this to happen, the item needs to be equipped in an armor slot. The absorbed damage reduces the item's durability, with any excess damage being ignored. Because of this, the item also needs a {@code minecraft:durability} component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DamageAbsorption(
    /* List of damage causes that can be absorbed by the item. By default, no damage cause is absorbed. */
    @JsonProperty("absorbable_causes") List<EntityDamageSource> absorbableCauses
) {
}
