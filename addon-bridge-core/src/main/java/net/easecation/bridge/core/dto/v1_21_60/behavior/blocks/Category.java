package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

/* Determines which category this block/item will be placed under in the inventory and crafting table container screens. */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Category {
    @JsonProperty("construction") CONSTRUCTION,
    @JsonProperty("equipment") EQUIPMENT,
    @JsonProperty("items") ITEMS,
    @JsonProperty("nature") NATURE,
    @JsonProperty("none") NONE
}
