package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum JjDifficulty {
    @JsonProperty("easy") EASY,
    @JsonProperty("normal") NORMAL,
    @JsonProperty("hard") HARD,
    @JsonProperty("peaceful") PEACEFUL
}
