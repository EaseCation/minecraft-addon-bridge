package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The entity puts on the desired equipment. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EquipItem(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
