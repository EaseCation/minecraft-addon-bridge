package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CgiSeatsSpec(
    /*
 * Defines where riders are placed when dismounting this entity:
 * - "default", riders are placed on a valid ground position around the entity, or at the center of the entity's collision box if none is found.
 * - "on<i>top</i>center", riders are placed at the center of the top of the entity's collision box.
 */
    @JsonProperty("dismount_mode") @Nullable String dismountMode,
    /* Event to execute on the owner entity when an entity starts riding it. */
    @JsonProperty("on_rider_enter_event") @Nullable String onRiderEnterEvent,
    /* Event to execute on the owner entity when an entity stops riding it. */
    @JsonProperty("on_rider_exit_event") @Nullable String onRiderExitEvent,
    /* Can be used to set a different camera radius when in third person or third person front camera. Value 0.0 is ignored. */
    @JsonProperty("third_person_camera_radius") @Nullable Double thirdPersonCameraRadius,
    /* Adds springiness to the camera movement when the camera moves back to its radius after being pushed closer to the player by and obstacle. A higher value means a stiffer spring. Value 0.0 is ignored. */
    @JsonProperty("camera_relax_distance_smoothing") @Nullable Double cameraRelaxDistanceSmoothing,
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
