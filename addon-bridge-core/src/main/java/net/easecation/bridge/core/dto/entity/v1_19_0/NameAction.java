package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Describes the special names for this entity and the events to call when the entity acquires those names. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NameAction(
    /* List of special names that will cause the events defined in {@code on_named} to fire. */
    @JsonProperty("name_filter") @Nullable String nameFilter,
    /* Event to be called when this entity acquires the name specified in `name_filter'. */
    @JsonProperty("on_named") @Nullable Event onNamed
) {
}
