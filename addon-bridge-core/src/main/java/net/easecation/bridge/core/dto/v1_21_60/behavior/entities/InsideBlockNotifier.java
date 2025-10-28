package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Verifies whether the entity is inside any of the listed blocks. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InsideBlockNotifier(
    /* List of blocks, with certain block states, that we are monitoring to see if the entity is inside. */
    @JsonProperty("block_list") @Nullable List<Object> blockList
) {
}
