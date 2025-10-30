package net.easecation.bridge.core.dto.item.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TransformItem(
    /* UNDOCUMENTED. */
    @JsonProperty("transform") @Nullable String transform
) {
}
