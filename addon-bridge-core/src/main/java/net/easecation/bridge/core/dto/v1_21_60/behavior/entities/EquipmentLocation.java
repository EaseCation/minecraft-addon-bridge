package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

/* The equipment location to test. */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EquipmentLocation {
    @JsonProperty("any") ANY,
    @JsonProperty("armor") ARMOR,
    @JsonProperty("feet") FEET,
    @JsonProperty("hand") HAND,
    @JsonProperty("head") HEAD,
    @JsonProperty("inventory") INVENTORY,
    @JsonProperty("leg") LEG,
    @JsonProperty("torse") TORSE
}
