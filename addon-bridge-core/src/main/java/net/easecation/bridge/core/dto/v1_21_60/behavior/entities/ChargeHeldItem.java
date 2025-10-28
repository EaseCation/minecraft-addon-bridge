package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows an entity to charge and use their held item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ChargeHeldItem(
    @JsonProperty("priority") @Nullable Priority priority,
    /* The list of items that can be used to charge the held item. This list is required and must have at least one item in it. */
    @JsonProperty("items") @Nullable List<EntitiesBb> items
) {
}
