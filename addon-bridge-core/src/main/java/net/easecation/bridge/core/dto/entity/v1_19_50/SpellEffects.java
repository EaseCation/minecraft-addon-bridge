package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines what mob effects to add and remove to the entity when adding this component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpellEffects(
    /* List of effects to add to this entity after adding this component. */
    @JsonProperty("add_effects") @Nullable List<Object> addEffects,
    /* List of identifiers of effects to be removed from this entity after adding this component. */
    @JsonProperty("remove_effects") @Nullable Object removeEffects
) {
}
