package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to send an event to another mob. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SendEvent(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Time in seconds for the entire event sending process. */
    @JsonProperty("cast_duration") @Nullable Double castDuration,
    /* If true, the mob will face the entity it sends an event to. */
    @JsonProperty("look_at_target") @Nullable Boolean lookAtTarget,
    /* List of spells for the mob to use. */
    @JsonProperty("event_choices") @Nullable List<Object> eventChoices,
    /* List of steps for the spell. */
    @JsonProperty("sequence") @Nullable EbhSequence sequence
) {
}
