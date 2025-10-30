package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FollowRange(
    /* UNDOCUMENTED. */
    @JsonProperty("value") @Nullable Integer value,
    /* UNDOCUMENTED. */
    @JsonProperty("max") @Nullable Integer max
) {
}
