package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Specifies if/how a mob burns in daylight. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Bribeable(
    /* Time in seconds before the Entity can be bribed again. */
    @JsonProperty("bribe_cooldown") @Nullable Double bribeCooldown,
    /* The list of items that can be used to bribe the entity. */
    @JsonProperty("bribe_items") @Nullable List<String> bribeItems
) {
}
