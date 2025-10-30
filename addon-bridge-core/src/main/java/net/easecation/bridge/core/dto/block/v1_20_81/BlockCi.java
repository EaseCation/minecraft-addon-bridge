package net.easecation.bridge.core.dto.block.v1_20_81;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BlockCi(
    @JsonProperty("values") Values values
) {
    
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Values(
            /* The lowest integer this state supports. This is also used as the default state value. */
            @JsonProperty("min") Integer min,
            /* The highest integer this state supports. This cannot be more than 15 above the minimum. */
            @JsonProperty("max") Integer max
        ) {
        }
}
