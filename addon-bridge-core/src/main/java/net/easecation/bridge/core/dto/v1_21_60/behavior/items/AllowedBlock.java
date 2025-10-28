package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A block descriptor that allows to be placed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AllowedBlock(
    /* Tags. */
    @JsonProperty("tags") @Nullable String tags
) {
}
