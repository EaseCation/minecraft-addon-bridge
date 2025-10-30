package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A entity type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DggEntityType(
    /* Conditions that make this target a valid type. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* To be a valid target choice, the target type cannot be farther away from this entity than {@code max_dist}. */
    @JsonProperty("max_dist") @Nullable Double maxDist,
    /* Determines if target-validity requires this entity to be in range only, or both in range and in sight. */
    @JsonProperty("must_see") @Nullable Boolean mustSee,
    /* Time (in seconds) the target must not be seen by this entity to become invalid. Used only if {@code must_see} is true. */
    @JsonProperty("must_see_forget_duration") @Nullable Boolean mustSeeForgetDuration
) {
}
