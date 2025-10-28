package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.processors;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AppendLoot(
    @JsonProperty("loot_table") String lootTable,
    @JsonProperty("type") String type
) {
}
