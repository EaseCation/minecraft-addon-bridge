package net.easecation.bridge.core.dto.item.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Repairable item component: how much damage can this item repair, what items can repair it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Repairable(
    /* Event that is called when this item has been repaired. */
    @JsonProperty("on_repaired") @Nullable net.easecation.bridge.core.dto.EmptyObject onRepaired,
    /* Repair item entries. */
    @JsonProperty("repair_items") @Nullable List<Object> repairItems
) {
}
