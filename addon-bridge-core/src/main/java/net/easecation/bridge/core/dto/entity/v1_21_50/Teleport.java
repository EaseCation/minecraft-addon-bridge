package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines an entity's teleporting behavior. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Teleport(
    /* Modifies the chance that the entity will teleport if the entity is in darkness. */
    @JsonProperty("dark_teleport_chance") @Nullable Double darkTeleportChance,
    /* Modifies the chance that the entity will teleport if the entity is in daylight. */
    @JsonProperty("light_teleport_chance") @Nullable Double lightTeleportChance,
    /* Maximum amount of time in seconds between random teleports. */
    @JsonProperty("max_random_teleport_time") @Nullable Double maxRandomTeleportTime,
    /* Minimum amount of time in seconds between random teleports. */
    @JsonProperty("min_random_teleport_time") @Nullable Double minRandomTeleportTime,
    /* Entity will teleport to a random position within the area defined by this cube. */
    @JsonProperty("random_teleport_cube") @Nullable List<Double> randomTeleportCube,
    /* If true, the entity will teleport randomly. */
    @JsonProperty("random_teleports") @Nullable Boolean randomTeleports,
    /* Maximum distance the entity will teleport when chasing a target. */
    @JsonProperty("target_distance") @Nullable Double targetDistance,
    /* The chance that the entity will teleport between 0.0 and 1.0. 1.0 means 100% */
    @JsonProperty("target_teleport_chance") @Nullable Double targetTeleportChance
) {
}
