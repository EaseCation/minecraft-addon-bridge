package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A nearby block requirements to get the entity into the {@code love} state. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EnvironmentRequirements(
    /* The block types required nearby for the entity to breed. */
    @JsonProperty("blocks") @Nullable Object blocks,
    /* The number of the required block types nearby for the entity to breed. */
    @JsonProperty("count") @Nullable Double count,
    /* How many blocks radius from the mob's center to search in for the required blocks. Bounded between 0 and 16. */
    @JsonProperty("radius") @Nullable Double radius
) {
}
