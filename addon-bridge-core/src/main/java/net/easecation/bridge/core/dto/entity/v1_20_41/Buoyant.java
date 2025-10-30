package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Enables an entity to float on the specified liquid blocks. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Buoyant(
    /* Base buoyancy used to calculate how much will a mob float. */
    @JsonProperty("base_buoyancy") @Nullable Double baseBuoyancy,
    /* Applies gravity each tick. Causes more of a wave simulation, but will cause more gravity to be applied outside liquids. */
    @JsonProperty("apply_gravity") @Nullable Boolean applyGravity,
    /* Base buoyancy used to calculate how much will a mob float. */
    @JsonProperty("buoyancy") @Nullable Double buoyancy,
    /* Probability for a big wave hitting the entity. Only used if {@code simulate_waves} is true. */
    @JsonProperty("big_wave_probability") @Nullable Double bigWaveProbability,
    /* Multiplier for the speed to make a big wave. Triggered depending on {@code big<i>wave</i>probability}. */
    @JsonProperty("big_wave_speed") @Nullable Double bigWaveSpeed,
    /* How much an actor will be dragged down when the Buoyancy Component is removed. */
    @JsonProperty("drag_down_on_buoyancy_removed") @Nullable Double dragDownOnBuoyancyRemoved,
    /* List of blocks this entity can float on. Must be a liquid block. */
    @JsonProperty("liquid_blocks") @Nullable List<BlockReference> liquidBlocks,
    /* Should the movement simulate waves going through. */
    @JsonProperty("simulate_waves") @Nullable Boolean simulateWaves
) {
}
