package net.easecation.bridge.core.dto.feature.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * 'minecraft:aggregate_feature` places a collection of features in an arbitary order. All features in the collection use the same input position. Features should not depend on each other, as there is no guarantee on the order the features will be placed.
 *  Succeeds if: At lease one feature is placed successfully.
 *  Fails if: All features fail to be placed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AggregateFeature(
    @JsonProperty("description") Description description,
    /* Collection of features to be placed one by one. No guarantee of order. All features use the same input position. */
    @JsonProperty("features") List<String> features,
    /* Do not continue placing features once either the first success or first failure has occurred. */
    @JsonProperty("early_out") @Nullable String earlyOut
) {
}
