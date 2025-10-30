package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines if the entity ticks the world and the radius around it to tick. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TickWorld(
    /* The distance at which the closest player has to be before this entity despawns. This option will be ignored if never_despawn is true. Min: 128 blocks. */
    @JsonProperty("distance_to_players") @Nullable Double distanceToPlayers,
    /* If true, this entity will not despawn even if players are far away. If false, distance<i>to</i>players will be used to determine when to despawn. */
    @JsonProperty("never_despawn") @Nullable Boolean neverDespawn,
    /* The area around the entity to tick. Default: 2. Allowed range: 2-6. */
    @JsonProperty("radius") @Nullable Integer radius
) {
}
