package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines what events to call when this entity is damaged by specific entities or items. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DamageSensor(
    /* The list of triggers that fire when the environment conditions match the given filter criteria. */
    @JsonProperty("triggers") @Nullable Object triggers
) {
}
