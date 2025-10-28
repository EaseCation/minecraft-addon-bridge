package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DcRarities {
    @JsonProperty("common") COMMON,
    @JsonProperty("uncommon") UNCOMMON,
    @JsonProperty("rare") RARE,
    @JsonProperty("epic") EPIC
}
