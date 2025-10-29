package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* This component allows the players to specify which biomes the mob spawns in. Each biome in the game has one or more tags. These tags are used to determine what biomes mobs spawn in. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BiomeFilter {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BiomeFilter_Variant0(
        List<Filters> value
    ) implements BiomeFilter {
    }
}
