package net.easecation.bridge.core.dto.loot_table.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Enchantment(
    @JsonProperty("id") @Nullable String id,
    @JsonProperty("level") @Nullable Object level
) {
}
