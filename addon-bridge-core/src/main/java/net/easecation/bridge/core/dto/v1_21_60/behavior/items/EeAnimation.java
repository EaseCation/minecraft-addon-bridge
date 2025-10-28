package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EeAnimation {
    @JsonProperty("bow") BOW,
    @JsonProperty("brush") BRUSH,
    @JsonProperty("camera") CAMERA,
    @JsonProperty("crossbow") CROSSBOW,
    @JsonProperty("drink") DRINK,
    @JsonProperty("eat") EAT,
    @JsonProperty("none") NONE,
    @JsonProperty("spear") SPEAR,
    @JsonProperty("spyglass") SPYGLASS
}
