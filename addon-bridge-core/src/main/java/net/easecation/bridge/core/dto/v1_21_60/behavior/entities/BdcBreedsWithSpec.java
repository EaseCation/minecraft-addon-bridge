package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* An entity definitions that this entity can breed with. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BdcBreedsWithSpec(
    /* The entity definition of this entity's babies. */
    @JsonProperty("baby_type") @Nullable String babyType,
    /* Event to run when this entity breeds. */
    @JsonProperty("breed_event") @Nullable Event breedEvent,
    /* The entity definition of this entity's mate. */
    @JsonProperty("mate_type") @Nullable String mateType
) {
}
