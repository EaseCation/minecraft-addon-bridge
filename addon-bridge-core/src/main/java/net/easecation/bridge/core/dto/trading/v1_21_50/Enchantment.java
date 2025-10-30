package net.easecation.bridge.core.dto.trading.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Enchantment(
    @JsonProperty("id") @Nullable String id,
    @JsonProperty("level") @Nullable Object level
) {
}
