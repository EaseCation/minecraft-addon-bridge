package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* An entity definitions that this entity can breed with. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BbaBreedsWithSpec(
    /* The entity definition of this entity's babies. */
    @JsonProperty("baby_type") @Nullable String babyType,
    /* Event to run when this entity breeds. */
    @JsonProperty("breed_event") @Nullable Event breedEvent,
    /* The entity definition of this entity's mate. */
    @JsonProperty("mate_type") @Nullable String mateType
) {
}
