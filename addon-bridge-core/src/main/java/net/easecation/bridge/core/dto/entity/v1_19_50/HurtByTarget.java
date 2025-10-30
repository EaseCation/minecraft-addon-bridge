package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to target another mob that hurts them. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HurtByTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    /* List of entity types that this mob can target if they hurt their owner. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* If true, nearby mobs of the same type will be alerted about the damage. */
    @JsonProperty("alert_same_type") @Nullable Boolean alertSameType,
    /* If true, the mob will hurt its owner and other mobs with the same owner as itself. */
    @JsonProperty("hurt_owner") @Nullable Boolean hurtOwner
) {
}
