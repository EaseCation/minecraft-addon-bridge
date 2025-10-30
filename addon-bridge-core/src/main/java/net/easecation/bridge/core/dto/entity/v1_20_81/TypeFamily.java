package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* Defines the families this entity belongs to. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TypeFamily(
    /* List of family names. */
    @JsonProperty("family") List<String> family
) {
}
