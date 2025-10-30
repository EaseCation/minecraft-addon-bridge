package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Compares the distance to the nearest Player with a float value. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DistanceToNearestPlayer(
    /* Compares the distance to the nearest Player with a float value. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* (Required) A floating point value. */
    @JsonProperty("value") Double value
) {
}
