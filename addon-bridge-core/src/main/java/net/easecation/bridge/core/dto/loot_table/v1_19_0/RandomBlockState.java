package net.easecation.bridge.core.dto.loot_table.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function random<i>block</i>state. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomBlockState(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("block_state") @Nullable String blockState,
    /* UNDOCUMENTED. */
    @JsonProperty("values") @Nullable Values values
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Values(
            @JsonProperty("min") @Nullable Integer min,
            @JsonProperty("max") @Nullable Integer max
        ) {
        }
}
