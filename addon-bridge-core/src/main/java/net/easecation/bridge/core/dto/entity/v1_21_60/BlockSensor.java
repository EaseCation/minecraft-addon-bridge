package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Fires off a specified event when a block in the block list is broken within the sensor range. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlockSensor(
    /* The maximum radial distance in which a specified block can be detected. The biggest radius is 32.0. */
    @JsonProperty("sensor_radius") @Nullable Integer sensorRadius,
    /* Blocks that will trigger the component when broken and what event will trigger. */
    @JsonProperty("on_break") @Nullable List<Object> onBreak,
    /* List of sources that break the block to listen for. If none are specified, all block breaks will be detected. */
    @JsonProperty("sources") @Nullable List<Filters> sources
) {
}
