package net.easecation.bridge.core.dto.item.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A block descriptor that allows to be placed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AllowedBlock(
    /* Tags. */
    @JsonProperty("tags") @Nullable String tags
) {
}
