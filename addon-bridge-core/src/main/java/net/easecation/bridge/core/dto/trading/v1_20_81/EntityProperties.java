package net.easecation.bridge.core.dto.trading.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the actor properties defined were executed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntityProperties(
    /* Returns true if the actor properties defined were executed. */
    @JsonProperty("condition") @Nullable String condition,
    /* The entity to test. The value must be only {@code this}. */
    @JsonProperty("entity") @Nullable String entity,
    /* The entity's properties. {@code on<i>fire}, {@code on</i>ground} is used for now. */
    @JsonProperty("properties") @Nullable Properties properties
) {
    
        /* The entity's properties. {@code on<i>fire}, {@code on</i>ground} is used for now. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Properties(
            /* Checks if the entity is on fire or not. */
            @JsonProperty("on_fire") @Nullable Boolean onFire,
            /* Checks if the entity is on the ground or not. */
            @JsonProperty("on_ground") @Nullable Boolean onGround
        ) {
        }
}
