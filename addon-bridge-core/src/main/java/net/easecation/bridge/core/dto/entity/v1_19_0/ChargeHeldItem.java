package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows an entity to charge and use their held item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ChargeHeldItem(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The list of items that can be used to charge the held item. This list is required and must have at least one item in it. */
    @JsonProperty("items") @Nullable List<String> items
) {
}
