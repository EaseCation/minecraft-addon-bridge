package net.easecation.bridge.core.dto.spawn_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This component allows players to set a priority for how often that mob should spawn. Mobs with lower weight values will have a lower chance to spawn than mobs with higher weight values. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Weight(
    /* This is the priority of the mob spawning out of 100. */
    @JsonProperty("default") @Nullable Integer defaultField,
    /* UNDOCUMENTED. */
    @JsonProperty("rarity") @Nullable Integer rarity
) {
}
