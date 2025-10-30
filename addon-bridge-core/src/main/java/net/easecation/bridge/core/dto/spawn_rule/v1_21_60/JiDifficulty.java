package net.easecation.bridge.core.dto.spawn_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JiDifficulty(
    String value
) {
}
