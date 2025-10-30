package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to dive underwater. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SquidDive(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
