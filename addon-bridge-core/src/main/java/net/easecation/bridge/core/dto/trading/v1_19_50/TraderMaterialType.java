package net.easecation.bridge.core.dto.trading.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function random_dye. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TraderMaterialType(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function
) {
}
