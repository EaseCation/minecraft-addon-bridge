package net.easecation.bridge.core.dto.trading.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function looting_enchant. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LootingEnchant(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("count") @Nullable Count count
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Count(
            @JsonProperty("min") @Nullable Integer min,
            @JsonProperty("max") @Nullable Integer max
        ) {
        }
}
