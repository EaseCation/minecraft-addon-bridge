package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the block has the given name. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsBlock(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The Family name to look for. */
    @JsonProperty("value") String value
) {
}
