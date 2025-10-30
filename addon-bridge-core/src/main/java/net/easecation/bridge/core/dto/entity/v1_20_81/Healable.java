package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the interactions with this entity for healing it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Healable(
    @JsonProperty("filters") @Nullable Filters filters,
    /* Determines if item can be used regardless of entity being at full health. */
    @JsonProperty("force_use") @Nullable Boolean forceUse,
    /* The array of items that can be used to heal this entity. */
    @JsonProperty("items") @Nullable List<Object> items
) {
}
