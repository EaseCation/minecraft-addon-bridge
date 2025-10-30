package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Sets the equipment table to use for the entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Equipment(
    /* A list of slots with the chance to drop an equipped item from that slot. */
    @JsonProperty("slot_drop_chance") @Nullable List<Object> slotDropChance,
    /* The file path to the equipment table, relative to the behavior pack's root. */
    @JsonProperty("table") @Nullable String table
) {
}
