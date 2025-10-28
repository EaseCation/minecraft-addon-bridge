package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Repairable item component: how much damage can this item repair, what items can repair it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Repairable(
    /* Repair item entries. */
    @JsonProperty("repair_items") @Nullable List<Object> repairItems
) {
}
