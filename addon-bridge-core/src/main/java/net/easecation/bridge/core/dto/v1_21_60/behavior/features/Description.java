package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;

/* Feature's description containing the identifier */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Description(
    /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
    @JsonProperty("identifier") String identifier
) {
}
