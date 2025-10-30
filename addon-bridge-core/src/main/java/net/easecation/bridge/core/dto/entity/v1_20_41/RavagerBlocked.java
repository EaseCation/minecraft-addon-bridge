package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the ravager's response to their melee attack being blocked. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RavagerBlocked(
    /* The strength with which blocking entities should be knocked back. */
    @JsonProperty("knockback_strength") @Nullable Double knockbackStrength,
    /* A list of weighted responses to the melee attack being blocked. */
    @JsonProperty("reaction_choices") @Nullable List<Object> reactionChoices
) {
}
