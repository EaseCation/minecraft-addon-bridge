package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sniff compels this entity to detect the nearest player within "sniffing<i>radius" and update its minecraft:suspect</i>tracking component state. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Sniff(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Cooldown range between sniffs in seconds. */
    @JsonProperty("cooldown_range") @Nullable VectorOf2Items cooldownRange,
    /* Sniffing duration in seconds */
    @JsonProperty("duration") @Nullable Double duration,
    /* Mob detection radius. */
    @JsonProperty("sniffing_radius") @Nullable Double sniffingRadius,
    /* Mob suspicion horizontal radius. When a player is within this radius horizontally, the anger level towards that player is increased. */
    @JsonProperty("suspicion_radius_horizontal") @Nullable Double suspicionRadiusHorizontal,
    /* Mob suspicion vertical radius. When a player is within this radius vertically, the anger level towards that player is increased. */
    @JsonProperty("suspicion_radius_vertical") @Nullable Double suspicionRadiusVertical
) {
}
