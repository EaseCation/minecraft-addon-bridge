package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity has the sneak input held. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsSneakHeld(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
