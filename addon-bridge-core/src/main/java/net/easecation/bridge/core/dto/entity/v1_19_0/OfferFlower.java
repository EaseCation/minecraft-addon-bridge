package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to offer the player a flower like the Iron Golem does. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OfferFlower(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
