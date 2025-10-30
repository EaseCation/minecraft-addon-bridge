package net.easecation.bridge.core.dto.spawn_rule.v1_19_40;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IbDifficulty(
    String value
) {
}
