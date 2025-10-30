package net.easecation.bridge.core.dto.block.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Maps face or material_instance names in a geometry file to an actual material instance. Material instance can either be a full material instance or a name to another already defined instance */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MaterialInstances(
    @JsonProperty("*") @Nullable MaterialInstance all
) {
}
