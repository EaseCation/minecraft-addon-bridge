package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* [Experimental] Sets the particles that will be used when block is destroyed. This component can be omitted. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DestructionParticles(
    /* The texture name used for the particle. */
    @JsonProperty("texture") @Nullable String texture,
    /* Tint multiplied to the color. Tint method logic varies, but often refers to the "rain" and "temperature" of the biome the block is placed in to compute the tint. */
    @JsonProperty("tint_method") @Nullable TintMethod tintMethod
) {
}
