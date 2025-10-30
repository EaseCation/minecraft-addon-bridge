package net.easecation.bridge.core.dto.item.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Planter item component. planter items are items that can be planted. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlockPlacer(
    /* Set the placement block name for the planter item. */
    @JsonProperty("block") String block,
    /* List of block descriptors that contain blocks that this item can be used on. If left empty, all blocks will be allowed. */
    @JsonProperty("use_on") @Nullable List<AllowedBlock> useOn,
    /* Allows you to specify that this item should replace the default item created for the data-driven block it places. */
    @JsonProperty("replace_block_item") @Nullable Boolean replaceBlockItem
) {
}
