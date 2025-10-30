package net.easecation.bridge.core.dto.loot_table.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* If the item to return has a smelted crafting recipe and the loot table is triggered by an entity killed with fire. the result will be the smelted version of the item */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FurnaceSmelt(
    /* UNDOCUMENTED. */
    @JsonProperty("function") @Nullable String function,
    /* UNDOCUMENTED. */
    @JsonProperty("conditions") @Nullable List<Condition> conditions
) {
}
