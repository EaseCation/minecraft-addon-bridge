package net.easecation.bridge.core.dto.trading.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function enchant<i>with</i>levels. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EnchantWithLevels(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("levels") @Nullable Object levels,
    /* UNDOCUMENTED. */
    @JsonProperty("treasure") @Nullable Boolean treasure
) {
}
