package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EdgEventBase(
    @JsonProperty("filters") @Nullable Filters filters,
    /* Triggers additional events. */
    @JsonProperty("trigger") @Nullable Trigger trigger,
    /* What gets added when the event gets triggered. */
    @JsonProperty("add") @Nullable AddOrRemove add,
    /* What gets removed when the event gets triggered. */
    @JsonProperty("remove") @Nullable AddOrRemove remove,
    /* Randomly selects one of the following items based upon their weight and the total weights. */
    @JsonProperty("randomize") @Nullable List<Object> randomize,
    /* A series of filters and components to be added. */
    @JsonProperty("sequence") @Nullable List<Object> sequence
) {
}
