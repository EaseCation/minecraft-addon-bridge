package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Move control that causes the mob to jump as it moves with a specified delay between jumps. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MovementJump(
    /* Delay after landing when using the slime move control. */
    @JsonProperty("jump_delay") @Nullable List<Double> jumpDelay,
    /* The maximum number in degrees the mob can turn per tick. */
    @JsonProperty("max_turn") @Nullable Double maxTurn
) {
}
