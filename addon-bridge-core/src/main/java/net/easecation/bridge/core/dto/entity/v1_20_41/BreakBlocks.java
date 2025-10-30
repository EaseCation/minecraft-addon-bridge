package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Specifies the blocks that this entity can break as it moves around. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BreakBlocks(
    /* A list of the blocks that can be broken as this entity moves around. */
    @JsonProperty("breakable_blocks") @Nullable List<String> breakableBlocks
) {
}
