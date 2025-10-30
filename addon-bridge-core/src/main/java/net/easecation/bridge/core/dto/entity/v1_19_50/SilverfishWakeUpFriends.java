package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to alert mobs in nearby blocks to come out. Currently it can only be used by Silverfish. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SilverfishWakeUpFriends(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
