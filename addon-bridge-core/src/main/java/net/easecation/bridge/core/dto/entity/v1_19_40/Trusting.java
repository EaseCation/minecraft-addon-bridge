package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the rules for a mob to trust players. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Trusting(
    /* The chance of the entity trusting with each item use between 0.0 and 1.0, where 1.0 is 100% */
    @JsonProperty("probability") @Nullable Double probability,
    /* Event to run when this entity becomes trusting. */
    @JsonProperty("trust_event") @Nullable Event trustEvent,
    /* The list of items that can be used to get the entity to trust players. */
    @JsonProperty("trust_items") @Nullable List<String> trustItems
) {
}
