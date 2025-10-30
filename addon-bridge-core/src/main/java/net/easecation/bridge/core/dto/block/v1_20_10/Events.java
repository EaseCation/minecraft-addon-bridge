package net.easecation.bridge.core.dto.block.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Events(
    @JsonProperty("minecraft:on_fall_on") @Nullable Event minecraft_onFallOn,
    @JsonProperty("minecraft:on_interact") @Nullable Event minecraft_onInteract,
    @JsonProperty("minecraft:on_placed") @Nullable Event minecraft_onPlaced,
    @JsonProperty("minecraft:on_player_destroyed") @Nullable Event minecraft_onPlayerDestroyed,
    @JsonProperty("minecraft:on_player_placing") @Nullable Event minecraft_onPlayerPlacing,
    @JsonProperty("minecraft:on_step_off") @Nullable Event minecraft_onStepOff,
    @JsonProperty("minecraft:on_step_on") @Nullable Event minecraft_onStepOn
) {
}
