package net.easecation.bridge.core.dto.v1_21_60.behavior.loot_tables;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function random_dye. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TraderMaterialType(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function
) {
}
