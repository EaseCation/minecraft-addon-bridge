package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* A component that fires an event when a set of conditions are met by other entities within the defined range. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface EntitySensor {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntitySensor_Variant0(
        /* If true the sensor range is additive on top of the entity's size. */
        @JsonProperty("relative_range") @Nullable Boolean relativeRange
    ) implements EntitySensor {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntitySensor_Variant1(
        /* The list of subsensors. */
        @JsonProperty("subsensors") @Nullable List<EntitySensor> subsensors,
        /* If true the sensor range is additive on top of the entity's size. */
        @JsonProperty("relative_range") @Nullable Boolean relativeRange,
        /* Limits the search to Players only for all subsensors. */
        @JsonProperty("find_players_only") @Nullable Boolean findPlayersOnly
    ) implements EntitySensor {
    }
}
