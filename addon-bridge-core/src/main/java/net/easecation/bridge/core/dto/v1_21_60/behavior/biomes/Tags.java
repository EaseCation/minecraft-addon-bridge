package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* Attach arbitrary string tags to this biome. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Tags(
    /* Array of string tags used by other systems such as entity spawning */
    @JsonProperty("tags") List<String> tags
) {
}
