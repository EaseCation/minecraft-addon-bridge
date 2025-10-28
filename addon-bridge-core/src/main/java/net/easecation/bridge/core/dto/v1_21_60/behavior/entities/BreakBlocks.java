package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Specifies the blocks that this entity can break as it moves around. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BreakBlocks(
    /* A list of the blocks that can be broken as this entity moves around. */
    @JsonProperty("breakable_blocks") @Nullable List<BlockName> breakableBlocks
) {
}
