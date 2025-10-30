package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to select a valid target entity that pushed it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TargetWhenPushed(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The list of conditions the other entity must meet to be a valid target. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* Probability that the entity will target the entity that pushed it. */
    @JsonProperty("percent_chance") @Nullable Double percentChance
) {
}
