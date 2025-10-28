package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows this mob to look at and follow the player that holds food they like. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Beg(
    @JsonProperty("priority") @Nullable Integer priority,
    /* List of items that this mob likes. */
    @JsonProperty("items") @Nullable List<EntitiesBb> items,
    /* Distance in blocks the mob will beg from. */
    @JsonProperty("look_distance") @Nullable Double lookDistance,
    /* The range of time in seconds this mob will stare at the player holding a food they like, begging for it. */
    @JsonProperty("look_time") @Nullable Range_a_B_ lookTime
) {
}
