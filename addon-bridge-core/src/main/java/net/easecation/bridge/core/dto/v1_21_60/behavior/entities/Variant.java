package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Used to differentiate the component group of a variant of an entity from others (e.g. ocelot, villager) Parameters */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Variant(
    /* The ID of the variant. By convention, 0 is the ID of the base entity */
    @JsonProperty("value") Integer value
) {
}
