package net.easecation.bridge.core.dto.v1_21_60.behavior.trading;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function enchant_randomly. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EnchantRandomly(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* Supports the optional treasure boolean (true/false) to allow treasure enchantments to be toggled on and off. */
    @JsonProperty("treasure") @Nullable Boolean treasure
) {
}
