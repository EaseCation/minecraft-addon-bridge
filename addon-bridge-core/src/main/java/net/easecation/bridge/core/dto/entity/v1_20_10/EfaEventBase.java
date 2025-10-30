package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EfaEventBase(
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
    @JsonProperty("sequence") @Nullable List<Object> sequence,
    /* Sets a property on the entity. */
    @JsonProperty("set_property") @Nullable Map<String, Object> setProperty
) {
}
