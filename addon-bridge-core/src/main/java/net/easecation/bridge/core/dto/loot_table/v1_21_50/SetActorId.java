package net.easecation.bridge.core.dto.loot_table.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set<i>actor</i>id. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetActorId(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("id") @Nullable String id
) {
}
