package net.easecation.bridge.core.dto.v1_21_60.behavior.trading;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Enchantment(
    @JsonProperty("id") @Nullable IdEnchantment id,
    @JsonProperty("level") @Nullable Object level
) {
}
