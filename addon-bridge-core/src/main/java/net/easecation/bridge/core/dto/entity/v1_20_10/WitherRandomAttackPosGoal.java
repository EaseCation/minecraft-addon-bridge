package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the wither to launch random attacks. Can only be used by the Wither Boss. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WitherRandomAttackPosGoal(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
