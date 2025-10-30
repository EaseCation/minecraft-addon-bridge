package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Adds a timer after which an event will fire. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Timer(
    /* If true, the timer will restart every time after it fires. */
    @JsonProperty("looping") @Nullable Boolean looping,
    /* If true, the amount of time on the timer will be random between the Minimum and Maximum values specified in time. */
    @JsonProperty("randomInterval") @Nullable Boolean randominterval,
    /* Amount of time in seconds for the timer. Can be specified as a number or a pair of numbers (Minimum and max). Incompatible with random<i>time</i>choices. */
    @JsonProperty("time") @Nullable Object time,
    /* Event to fire when the time on the timer runs out. */
    @JsonProperty("time_down_event") @Nullable Event timeDownEvent,
    /* This is a list of objects, representing one value in seconds that can be picked before firing the event and an optional weight. Incompatible with time. */
    @JsonProperty("random_time_choices") @Nullable List<Object> randomTimeChoices
) {
}
