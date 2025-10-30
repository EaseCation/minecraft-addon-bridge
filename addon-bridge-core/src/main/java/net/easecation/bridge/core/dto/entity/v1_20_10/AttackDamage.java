package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AttackDamage(
    /* UNDOCUMENTED: value. */
    @JsonProperty("value") Double value
) {
}
