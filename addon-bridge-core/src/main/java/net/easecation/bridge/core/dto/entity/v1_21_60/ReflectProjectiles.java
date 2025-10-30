package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* [EXPERIMENTAL] Allows an entity to reflect projectiles. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ReflectProjectiles(
    /* A Molang expression defining the angle in degrees to add to the projectile's y axis rotation. */
    @JsonProperty("azimuth_angle") @Nullable Object azimuthAngle,
    /* A Molang expression defining the angle in degrees to add to the projectile's x axis rotation. */
    @JsonProperty("elevation_angle") @Nullable Object elevationAngle,
    /* An array of strings defining the types of projectiles that are reflected when they hit the entity. */
    @JsonProperty("reflected_projectiles") @Nullable List<String> reflectedProjectiles,
    /* A Molang expression defining the velocity scaling of the reflected projectile. Values below 1 decrease the projectile's velocity, and values above 1 increase it. */
    @JsonProperty("reflection_scale") @Nullable Object reflectionScale,
    /* A string defining the name of the sound event to be played when a projectile is reflected. "reflect" unless specified. */
    @JsonProperty("reflection_sound") @Nullable String reflectionSound
) {
}
