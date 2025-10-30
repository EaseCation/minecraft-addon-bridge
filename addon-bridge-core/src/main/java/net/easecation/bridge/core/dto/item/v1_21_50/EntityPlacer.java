package net.easecation.bridge.core.dto.item.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Entity placer item component. You can specifiy allowed blocks that the item is restricted to. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntityPlacer(
    /* The entity to be placed in the world. */
    @JsonProperty("entity") String entity,
    /* List of block descriptors that contain blocks that this item can be dispensed on. If left empty, all blocks will be allowed. */
    @JsonProperty("dispense_on") @Nullable List<Object> dispenseOn,
    /* List of block descriptors that contain blocks that this item can be used on. If left empty, all blocks will be allowed. */
    @JsonProperty("use_on") @Nullable List<Object> useOn
) {
}
