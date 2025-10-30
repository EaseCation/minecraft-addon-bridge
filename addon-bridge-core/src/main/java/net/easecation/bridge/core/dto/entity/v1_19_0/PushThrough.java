package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets the distance through which the entity can push through. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PushThrough(
    /* The value of the entity's push-through, in blocks. */
    @JsonProperty("value") @Nullable Double value
) {
}
