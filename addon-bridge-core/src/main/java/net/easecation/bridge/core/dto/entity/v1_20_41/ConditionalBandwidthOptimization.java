package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the Conditional Spatial Update Bandwidth Optimizations of this entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ConditionalBandwidthOptimization(
    /* The object containing the conditional bandwidth optimization values. */
    @JsonProperty("conditional_values") @Nullable List<Object> conditionalValues,
    /* The object containing the default bandwidth optimization values. */
    @JsonProperty("default_values") @Nullable DefaultValues defaultValues
) {
    
        /* The object containing the default bandwidth optimization values. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record DefaultValues(
            /* In relation to the optimization value, determines the maximum ticks spatial update packets can be not sent. */
            @JsonProperty("max_dropped_ticks") @Nullable Integer maxDroppedTicks,
            /* The maximum distance considered during bandwidth optimizations. Any value below the Maximum is interpolated to find optimization, and any value greater than or equal to this Maximum results in Maximum optimization. */
            @JsonProperty("max_optimized_distance") @Nullable Double maxOptimizedDistance,
            /* When set to true, smaller motion packets will be sent during drop packet intervals, resulting in the same amount of packets being sent as without optimizations but with much less data being sent. This should be used when actors are travelling very quickly or teleporting to prevent visual oddities. */
            @JsonProperty("use_motion_prediction_hints") @Nullable Boolean useMotionPredictionHints
        ) {
        }
}
