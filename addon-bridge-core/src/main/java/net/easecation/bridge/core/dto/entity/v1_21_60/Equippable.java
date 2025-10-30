package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines an entity's behavior for having items equipped to it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Equippable(
    /* List of slots and the item that can be equipped. */
    @JsonProperty("slots") @Nullable List<Object> slots
) {
}
