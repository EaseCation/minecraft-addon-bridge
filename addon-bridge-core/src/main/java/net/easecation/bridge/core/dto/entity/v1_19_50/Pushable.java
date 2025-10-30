package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines what can push an entity between other entities and pistons. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Pushable(
    /* Whether the entity can be pushed by other entities. */
    @JsonProperty("is_pushable") @Nullable Boolean isPushable,
    /* Whether the entity can be pushed by pistons safely. */
    @JsonProperty("is_pushable_by_piston") @Nullable Boolean isPushableByPiston
) {
}
