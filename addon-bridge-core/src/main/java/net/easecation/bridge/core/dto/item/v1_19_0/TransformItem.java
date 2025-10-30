package net.easecation.bridge.core.dto.item.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TransformItem(
    /* UNDOCUMENTED. */
    @JsonProperty("transform") @Nullable String transform
) {
}
