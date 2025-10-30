package net.easecation.bridge.core.dto.lighting.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The properties of the atmosphere */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AtmosphericsDefinition(
    /* How the atmosphere is divided up */
    @JsonProperty("horizon_blend_stops") @Nullable HorizonBlendStops horizonBlendStops,
    /* How strong the atmosphere's rayleigh scattering term is */
    @JsonProperty("rayleigh_strength") @Nullable Double rayleighStrength,
    /* How strong the sun's mie scattering term is */
    @JsonProperty("sun_mie_strength") @Nullable Double sunMieStrength,
    /* How strong the moon's mie scattering term is */
    @JsonProperty("moon_mie_strength") @Nullable Double moonMieStrength,
    /* How the lobe of the mie scattering is shaped */
    @JsonProperty("sun_glare_shape") @Nullable Double sunGlareShape,
    /* The RGB color of the zenith region of the atmosphere */
    @JsonProperty("sky_zenith_color") @Nullable LightingColor skyZenithColor,
    /* The RGB color of the horizon region of the atmosphere */
    @JsonProperty("sky_horizon_color") @Nullable LightingColor skyHorizonColor
) {
    
        /* How the atmosphere is divided up */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record HorizonBlendStops(
            /* The minimum horizon height */
            @JsonProperty("min") @Nullable Double min,
            /* The height relative to the horizon where the zenith contribution will take over */
            @JsonProperty("start") @Nullable Double start,
            /* The height relative to the horizon where mie scattering begins */
            @JsonProperty("mie_start") @Nullable Double mieStart,
            /* The maximum horizon height */
            @JsonProperty("max") @Nullable Double max
        ) {
        }
}
