package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the health of the subject. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ActorHealth(
    /* Tests the health of the subject. */
    @JsonProperty("test") @Nullable String test,
    /* (Optional) The comparison to apply with {@code value}. */
    @JsonProperty("operator") @Nullable Operator operator,
    /* (Optional) The subject of this filter test. */
    @JsonProperty("subject") @Nullable Subject subject,
    /* (Required) A integer value. */
    @JsonProperty("value") Integer value
) {
}
