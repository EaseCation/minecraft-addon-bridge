package net.easecation.bridge.core.dto.v1_21_60.behavior.loot_tables;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The function random<i>aux</i>value. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomAuxValue(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("values") @Nullable Values values
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Values(
            /* UNDOCUMENTED. */
            @JsonProperty("min") @Nullable Integer min,
            /* UNDOCUMENTED. */
            @JsonProperty("max") @Nullable Integer max
        ) {
        }
}
