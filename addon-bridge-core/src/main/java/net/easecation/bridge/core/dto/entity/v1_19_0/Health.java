package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets the amount of health this mob has. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Health(
    /* The maximum health the entity can heal. */
    @JsonProperty("max") @Nullable Integer max,
    /* Current health of the entity. */
    @JsonProperty("value") @Nullable Object value
) {
}
