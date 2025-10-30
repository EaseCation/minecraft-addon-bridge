package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AttackDamage(
    /* UNDOCUMENTED: value. */
    @JsonProperty("value") Double value
) {
}
