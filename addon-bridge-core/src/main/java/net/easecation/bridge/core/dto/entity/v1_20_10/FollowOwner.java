package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to follow the player that owns them. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FollowOwner(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Specify if the mob can teleport to the player if it is too far away. */
    @JsonProperty("can_teleport") @Nullable Boolean canTeleport,
    /* Specify if the mob will follow the owner if it has heard a vibration lately. */
    @JsonProperty("ignore_vibration") @Nullable Boolean ignoreVibration,
    /* The maximum distance in blocks this mob can be from its owner to start following, only used when canTeleport is false. */
    @JsonProperty("max_distance") @Nullable Double maxDistance,
    /* The distance in blocks that the owner can be away from this mob before it starts following it. */
    @JsonProperty("start_distance") @Nullable Double startDistance,
    /* The distance in blocks this mob will stop from its owner while following it. */
    @JsonProperty("stop_distance") @Nullable Double stopDistance
) {
}
