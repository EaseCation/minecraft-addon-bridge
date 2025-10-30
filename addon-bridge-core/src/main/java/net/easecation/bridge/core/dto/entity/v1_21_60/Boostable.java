package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the conditions and behavior of a rideable entity's boost. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Boostable(
    /* Time in seconds for the boost. */
    @JsonProperty("duration") @Nullable Double duration,
    /* Factor by which the entity's normal speed increases. E.g. 2.0 means go twice as fast. */
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* List of items that can be used to boost while riding this entity. */
    @JsonProperty("boost_items") @Nullable List<Object> boostItems
) {
}
