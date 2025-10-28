package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* Defines the families this entity belongs to. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TypeFamily(
    /* List of family names. */
    @JsonProperty("family") List<String> family
) {
}
