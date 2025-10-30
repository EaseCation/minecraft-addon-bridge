package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the entity's size interpolation based on the entity's age. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ScaleByAge(
    /* Ending scale of the entity when it's fully grown. */
    @JsonProperty("end_scale") @Nullable Double endScale,
    /* Initial scale of the newborn entity. */
    @JsonProperty("start_scale") @Nullable Double startScale
) {
}
