package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows Guardians, Iron Golems and Villagers to move within their pre-defined area that the mob should be restricted to. Other mobs don't have a restriction defined. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveTowardsRestriction(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* UNDOCUMENTED: control flags. */
    @JsonProperty("control_flags") @Nullable List<String> controlFlags
) {
}
