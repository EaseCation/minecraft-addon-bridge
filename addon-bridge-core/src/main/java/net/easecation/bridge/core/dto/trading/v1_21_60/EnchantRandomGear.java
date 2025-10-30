package net.easecation.bridge.core.dto.trading.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function enchant<i>random</i>gear. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EnchantRandomGear(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* Takes a chance modifier to manipulate the algorithm. Note that a chance modifier of 1.0 doesn't mean a 100% chance that gear will become enchanted. */
    @JsonProperty("chance") @Nullable Double chance
) {
}
