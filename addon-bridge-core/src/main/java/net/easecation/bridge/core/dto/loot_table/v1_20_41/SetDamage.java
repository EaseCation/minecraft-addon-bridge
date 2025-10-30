package net.easecation.bridge.core.dto.loot_table.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function set_damage. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetDamage(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("damage") @Nullable Object damage
) {
}
