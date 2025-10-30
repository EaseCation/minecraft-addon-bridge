package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A component that fires an event when a set of conditions are met by other entities within the defined range. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntitySensor(
    /* The maximum number of entities that must pass the filter conditions for the event to send. */
    @JsonProperty("maximum_count") @Nullable Integer maximumCount,
    /* The minimum number of entities that must pass the filter conditions for the event to send. */
    @JsonProperty("minimum_count") @Nullable Integer minimumCount,
    /* If true the sensor range is additive on top of the entity's size. */
    @JsonProperty("relative_range") @Nullable Boolean relativeRange,
    /* If true requires all nearby entities to pass the filter conditions for the event to send. */
    @JsonProperty("require_all") @Nullable Boolean requireAll,
    /* The maximum distance another entity can be from this and have the filters checked against it. */
    @JsonProperty("sensor_range") @Nullable Double sensorRange,
    @JsonProperty("event_filters") @Nullable Filters eventFilters,
    /* event. */
    @JsonProperty("event") @Nullable String event
) {
}
