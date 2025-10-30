package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Sets the entity's delay between playing its ambient sound. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AmbientSoundInterval(
    /* Level sound event to be played as the ambient sound. */
    @JsonProperty("event_name") @Nullable String eventName,
    /* List of dynamic level sound events, with conditions for choosing between them. Evaluated in order, first one wins. If none evaluate to true, 'event_name' will take precedence. */
    @JsonProperty("event_names") @Nullable List<Object> eventNames,
    /* Maximum time in seconds to randomly add to the ambient sound delay time. */
    @JsonProperty("range") @Nullable Double range,
    /* Minimum time in seconds before the entity plays its ambient sound again. */
    @JsonProperty("value") @Nullable Double value
) {
}
