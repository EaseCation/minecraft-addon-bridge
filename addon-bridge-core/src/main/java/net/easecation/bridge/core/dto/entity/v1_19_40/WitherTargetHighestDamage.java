package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the wither to focus its attacks on whichever mob has dealt the most damage to it. Can only be used by the Wither Boss. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WitherTargetHighestDamage(
    @JsonProperty("priority") @Nullable Integer priority,
    /* List of entity types the wither takes into account to find who dealt the most damage to it. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes
) {
}
