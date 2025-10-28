package net.easecation.bridge.core.dto.v1_21_60.behavior.trading;

import com.fasterxml.jackson.annotation.*;

/* The function set<i>armor</i>trim. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetArmorTrim(
    /* UNDOCUMENTED. */
    @JsonProperty("function") String function,
    /* Trim's material */
    @JsonProperty("material") String material,
    /* Trim pattern */
    @JsonProperty("pattern") String pattern
) {
}
