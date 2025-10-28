package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

/* The subject of this filter test. */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Subject {
    @JsonProperty("block") BLOCK,
    @JsonProperty("other") OTHER,
    @JsonProperty("parent") PARENT,
    @JsonProperty("player") PLAYER,
    @JsonProperty("self") SELF,
    @JsonProperty("target") TARGET,
    @JsonProperty("damager") DAMAGER
}
