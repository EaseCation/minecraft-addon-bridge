package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets the entity's visual size. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Scale(
    /* The value of the scale. 1.0 means the entity will appear at the scale they are defined in their model. Higher numbers make the entity bigger */
    @JsonProperty("value") @Nullable Double value
) {
}
