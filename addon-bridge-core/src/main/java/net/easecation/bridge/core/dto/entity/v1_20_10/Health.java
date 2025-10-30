package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Specifies how much life an entity has when spawned. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Health(
    /* The maximum starting health an entity has. */
    @JsonProperty("max") @Nullable Integer max,
    /* The amount of health an entity to start with by default. */
    @JsonProperty("value") @Nullable Object value
) {
}
