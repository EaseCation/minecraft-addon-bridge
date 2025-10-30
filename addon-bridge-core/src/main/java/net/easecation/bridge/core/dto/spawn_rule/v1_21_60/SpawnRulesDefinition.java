package net.easecation.bridge.core.dto.spawn_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Data-Driven spawning allows you to adjust the spawn conditions of mobs. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpawnRulesDefinition(
    /* A version that tells minecraft what type of data format can be expected when reading this file. */
    @JsonProperty("format_version") String formatVersion,
    /* Data-Driven spawning allows you to adjust the spawn conditions of mobs. */
    @JsonProperty("minecraft:spawn_rules") Minecraft_spawnRules minecraft_spawnRules
) {
    
        /* Data-Driven spawning allows you to adjust the spawn conditions of mobs. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Minecraft_spawnRules(
            /* The descripton of to which entity this spawn rule belongs. */
            @JsonProperty("description") @Nullable Description description,
            /* UNDOCUMENTED. */
            @JsonProperty("conditions") @Nullable List<Object> conditions
        ) {
            
                /* The descripton of to which entity this spawn rule belongs. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Description(
                    /* The entity identifier this spawn rule will apply to, entity must exist. */
                    @JsonProperty("identifier") String identifier,
                    /* Setting an entity to a pool it will spawn as long as that pool hasn't reached the spawn limit. */
                    @JsonProperty("population_control") String populationControl
                ) {
                }
        }
}
