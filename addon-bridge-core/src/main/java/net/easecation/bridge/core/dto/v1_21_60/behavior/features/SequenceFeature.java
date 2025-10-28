package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/*
 * {@code minecraft:sequence_feature} places a collection of features sequentially, in the order they appear in data. The output position of the previous feature is used as the input position for the next. For example, a tree feature is placed at (0, 0, 0) and places blocks up to (0, 10, 0). The next feature in the sequence begins at (0, 10, 0).
 * Succeeds if: All features in the sequence are successfully placed.
 * Fails if: Any feature in the sequence fails to be placed. Features that have not yet been placed at the time of failure are skipped.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SequenceFeature(
    @JsonProperty("description") Description description,
    /* List of features to be placed in sequence. The output position of the previous feature is used as the input position to the next. */
    @JsonProperty("features") List<FeatureIdentifier> features
) {
}
