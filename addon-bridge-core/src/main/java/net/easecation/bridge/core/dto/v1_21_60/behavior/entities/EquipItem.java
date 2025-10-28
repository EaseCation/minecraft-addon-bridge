package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The entity puts on the desired equipment. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EquipItem(
    @JsonProperty("priority") @Nullable Priority priority
) {
}
