package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Block(
    /* The block id, for example: `minecraft:air'. */
    @JsonProperty("name") @Nullable String name,
    /* The block states. */
    @JsonProperty("states") @Nullable Map<String, Object> states
) {
}
