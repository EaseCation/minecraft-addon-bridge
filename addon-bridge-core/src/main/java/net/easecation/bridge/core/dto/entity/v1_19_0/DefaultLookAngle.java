package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets this entity's default head rotation angle. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DefaultLookAngle(
    /* Angle in degrees. */
    @JsonProperty("value") @Nullable Double value
) {
}
