package net.easecation.bridge.core.dto.loot_table.v1_19_0;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IdEnchantment(
    String value
) {
}
