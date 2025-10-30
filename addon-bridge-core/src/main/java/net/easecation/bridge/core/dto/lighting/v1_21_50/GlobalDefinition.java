package net.easecation.bridge.core.dto.lighting.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GlobalDefinition(
    @JsonProperty("format_version") @Nullable String formatVersion,
    /* The directional lights that affect the world */
    @JsonProperty("directional_lights") @Nullable DirectionalLights directionalLights
) {
    
        /* The directional lights that affect the world */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record DirectionalLights(
            /* The sun directional light */
            @JsonProperty("sun") @Nullable Sun sun,
            /* The moon directional light */
            @JsonProperty("moon") @Nullable Moon moon,
            /* The rotational offset of the sun and moon from their standard orbital axis; measured in degrees */
            @JsonProperty("orbital_offset_degrees") @Nullable Double orbitalOffsetDegrees,
            /* The point lights that affect the world */
            @JsonProperty("point_lights") @Nullable PointLights pointLights,
            /* The physically-based rendering properties for the world */
            @JsonProperty("pbr") @Nullable Pbr pbr
        ) {
            
                /* The sun directional light */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Sun(
                    /* How bright the sun is, measured in lux (lx) */
                    @JsonProperty("illuminance") @Nullable Double illuminance,
                    /* The RGB color that the sun contributes to direct surface lighting; supports RGB array or HEX string */
                    @JsonProperty("color") @Nullable LightingColor color
                ) {
                }
            
                /* The moon directional light */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Moon(
                    /* How bright the moon is, measured in lux (lx) */
                    @JsonProperty("illuminance") @Nullable Double illuminance,
                    /* The RGB color that the moon contributes to direct surface lighting; supports RGB array or HEX string */
                    @JsonProperty("color") @Nullable LightingColor color
                ) {
                }
            
                /* The point lights that affect the world */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record PointLights(
                    /* List of key-value pairs where the key is a namespace-qualified block name and the value is a color */
                    @JsonProperty("colors") @Nullable Map<String, LightingColor> colors
                ) {
                }
            
                /* The physically-based rendering properties for the world */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Pbr(
                    /* The PBR properties for blocks */
                    @JsonProperty("blocks") @Nullable Blocks blocks,
                    /* The PBR properties for actors */
                    @JsonProperty("actors") @Nullable Actors actors,
                    /* The PBR properties for particles */
                    @JsonProperty("particles") @Nullable Particles particles
                ) {
                    
                        /* The PBR properties for blocks */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record Blocks(
                            /* The default MER value to use for blocks when not defined via textureset; supports RGB array or HEX string */
                            @JsonProperty("color") @Nullable LightingColor color
                        ) {
                        }
                    
                        /* The PBR properties for actors */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record Actors(
                            /* The default MER value to use for actors/mobs when not defined via textureset; supports RGB array or HEX string */
                            @JsonProperty("color") @Nullable LightingColor color
                        ) {
                        }
                    
                        /* The PBR properties for particles */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record Particles(
                            /* The default MER value to use for particles when not defined via textureset; supports RGB array or HEX string */
                            @JsonProperty("color") @Nullable LightingColor color
                        ) {
                        }
                }
        }
}
