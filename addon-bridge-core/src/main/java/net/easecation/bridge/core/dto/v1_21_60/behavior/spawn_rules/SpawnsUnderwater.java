package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpawnsUnderwater(
    /* UNDOCUMENTED. */
    @JsonProperty("blocks") @Nullable Object blocks,
    /* UNDOCUMENTED. */
    @JsonProperty("distance") @Nullable Double distance
) {
}
