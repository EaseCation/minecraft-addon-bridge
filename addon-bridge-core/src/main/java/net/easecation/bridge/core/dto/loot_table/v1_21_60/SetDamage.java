package net.easecation.bridge.core.dto.loot_table.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set_damage. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetDamage(
    /* UNDOCUMENTED. */
    @JsonProperty("add") @Nullable Boolean add,
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("damage") @Nullable Object damage
) {
}
