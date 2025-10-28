package net.easecation.bridge.core.dto.v1_21_60.behavior.recipes;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Recipe definition 1.12.0 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Definition(
    /* UNDOCUMENTED. */
    @JsonProperty("identifier") @Nullable String identifier
) {
}
