package net.easecation.bridge.core.dto.feature.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * {@code minecraft:conditional_list} Places the first suitable feature within a collection.
 * These conditional features will be evaluated in order.
 * Succeeds if: A condition is successfully resolved.
 * Fails if: No condition is successfully resolved.
 * Example use: assigning a feature to an expression
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ConditionalList(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* Array of Features, and their associated Conditions, for attempted placement. These features will be evaluated as ordered. */
    @JsonProperty("conditional_features") List<Object> conditionalFeatures,
    /* Denote whether placement should end on first successful placement or first passed condition. */
    @JsonProperty("early_out_scheme") @Nullable String earlyOutScheme
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
