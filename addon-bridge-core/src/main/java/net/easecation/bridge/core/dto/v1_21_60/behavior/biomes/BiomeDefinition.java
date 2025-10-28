package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;

/* A custom biome definition. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BiomeDefinition(
    /* The description for this biome. */
    @JsonProperty("description") Description description,
    @JsonProperty("components") Components components
) {
    
        /* The description for this biome. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The identifier for this biome. The name must include a namespace and must not use the Minecraft namespace unless overriding a Vanilla biome. */
            @JsonProperty("identifier") Identifier identifier
        ) {
        }
}
