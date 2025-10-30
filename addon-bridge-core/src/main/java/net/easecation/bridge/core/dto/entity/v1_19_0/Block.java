package net.easecation.bridge.core.dto.entity.v1_19_0;

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
