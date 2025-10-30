package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to stay afloat while swimming. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Float_(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
