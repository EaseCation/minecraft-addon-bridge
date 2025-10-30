package net.easecation.bridge.core.dto.loot_table.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set_count. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetCount(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("count") @Nullable Object count
) {
}
