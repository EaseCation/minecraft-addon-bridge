package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

/* The comparison to apply with {@code value}. */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Operator {
    @JsonProperty("!=") NOT_EQUAL,
    @JsonProperty("<") LESS_THAN,
    @JsonProperty("<=") LESS_THAN_OR_EQUAL,
    @JsonProperty("<>") NOT_EQUAL_ALT,
    @JsonProperty("=") EQUAL,
    @JsonProperty("==") EQUAL_EQUAL,
    @JsonProperty(">") GREATER_THAN,
    @JsonProperty(">=") GREATER_THAN_OR_EQUAL,
    @JsonProperty("equals") EQUALS,
    @JsonProperty("not") NOT
}
