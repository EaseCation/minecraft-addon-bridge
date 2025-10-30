package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Can only be used by Villagers. Allows the mob to accept flowers from Iron Golems. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TakeFlower(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
