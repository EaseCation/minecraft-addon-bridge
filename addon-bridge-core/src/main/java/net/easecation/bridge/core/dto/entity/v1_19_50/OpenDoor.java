package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to open doors. Requires the mob to be able to path through doors, otherwise the mob won't even want to try opening them. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenDoor(
    @JsonProperty("priority") @Nullable Integer priority,
    /* If true, the mob will close the door after opening it and going through it. */
    @JsonProperty("close_door_after") @Nullable Boolean closeDoorAfter
) {
}
