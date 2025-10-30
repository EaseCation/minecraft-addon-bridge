package net.easecation.bridge.core.dto.trading.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* The function set_lore. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetLore(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("lore") @Nullable List<String> lore
) {
}
