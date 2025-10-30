package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to stay afloat while swimming. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Float_(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
