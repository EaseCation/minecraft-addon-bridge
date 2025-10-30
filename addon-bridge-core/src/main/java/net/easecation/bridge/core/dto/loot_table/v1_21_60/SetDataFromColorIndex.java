package net.easecation.bridge.core.dto.loot_table.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set<i>data</i>from<i>color</i>index. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetDataFromColorIndex(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function
) {
}
