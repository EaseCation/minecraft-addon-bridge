package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to go into stone blocks like Silverfish do. Currently it can only be used by Silverfish. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SilverfishMergeWithStone(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
