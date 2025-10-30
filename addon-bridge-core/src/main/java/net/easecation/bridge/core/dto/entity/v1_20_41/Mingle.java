package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to go to the village bell and mingle with other entities. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Mingle(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* Amount of time in seconds that the entity will chat with another entity. */
    @JsonProperty("duration") @Nullable Double duration,
    /* The distance from its partner that this entity will mingle. If the entity type is not the same as the entity, this value needs to be identical on both entities. */
    @JsonProperty("mingle_distance") @Nullable Double mingleDistance,
    /* The entity type that this entity is allowed to mingle with. */
    @JsonProperty("mingle_partner_type") @Nullable Object minglePartnerType
) {
}
