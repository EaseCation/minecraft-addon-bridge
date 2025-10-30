package net.easecation.bridge.core.dto.spawn_rule.v1_20_41;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JeDifficulty(
    String value
) {
}
