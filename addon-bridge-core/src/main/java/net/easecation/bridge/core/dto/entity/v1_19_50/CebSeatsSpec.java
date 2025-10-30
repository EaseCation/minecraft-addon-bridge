package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CebSeatsSpec(
    /* Angle in degrees that a rider is allowed to rotate while riding this entity. Omit this property for no limit */
    @JsonProperty("lock_rider_rotation") @Nullable Double lockRiderRotation,
    /* Defines the maximum number of riders that can be riding this entity for this seat to be valid. */
    @JsonProperty("max_rider_count") @Nullable Integer maxRiderCount,
    /* Defines the minimum number of riders that need to be riding this entity before this seat can be used. */
    @JsonProperty("min_rider_count") @Nullable Integer minRiderCount,
    /* Position of this seat relative to this entity's position. */
    @JsonProperty("position") @Nullable VectorOf3Items position,
    /* Offset to rotate riders by. */
    @JsonProperty("rotate_rider_by") @Nullable MolangNumber rotateRiderBy
) {
}
