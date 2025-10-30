package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* List of hitboxes for melee and ranged hits against the entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomHitTest(
    /* Defines a hitbox size and pivot to test against. */
    @JsonProperty("hitboxes") @Nullable List<Object> hitboxes
) {
}
