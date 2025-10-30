package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to emit {@code entityMove}, {@code swim} and {@code flap} game events, depending on the block the entity is moving through. It is added by default to every mob. Add it again to override its behavior. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GameEventMovementTracking(
    /* If true, the {@code flap} game event will be emitted when the entity moves through air. */
    @JsonProperty("emit_flap") @Nullable Boolean emitFlap,
    /* If true, the {@code entityMove} game event will be emitted when the entity moves on ground or through a solid. */
    @JsonProperty("emit_move") @Nullable Boolean emitMove,
    /* If true, the {@code swim} game event will be emitted when the entity moves through a liquid. */
    @JsonProperty("emit_swim") @Nullable Boolean emitSwim
) {
}
