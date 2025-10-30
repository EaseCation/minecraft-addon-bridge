package net.easecation.bridge.core.dto.spawn_rule.v1_19_40;

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
