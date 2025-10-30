package net.easecation.bridge.core.dto.loot_table.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set_name. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetName(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("name") @Nullable String name
) {
}
