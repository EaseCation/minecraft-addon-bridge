package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EgeWeightedEventBase(
    @JsonProperty("filters") @Nullable Filters filters,
    /* Triggers additional events. */
    @JsonProperty("trigger") @Nullable Trigger trigger,
    /* What gets added when the event gets triggered. */
    @JsonProperty("add") @Nullable AddOrRemove add,
    /* What gets removed when the event gets triggered. */
    @JsonProperty("remove") @Nullable AddOrRemove remove,
    /* Randomly selects one of the following items based upon their weight and the total weights. */
    @JsonProperty("randomize") @Nullable List<EgeWeightedEventBase> randomize,
    /* A series of filters and components to be added. */
    @JsonProperty("sequence") @Nullable List<EgeEventBase> sequence,
    /* UNDOCUMENTED */
    @JsonProperty("emit_vibration") @Nullable EmitVibration emitVibration,
    /* Sets a property on the entity. */
    @JsonProperty("set_property") @Nullable Map<String, Object> setProperty,
    /* Queues a command to be executed. */
    @JsonProperty("queue_command") @Nullable QueueCommand queueCommand,
    /* The weight on how likely this section is to trigger. */
    @JsonProperty("weight") @Nullable Double weight
) {
    
        /* UNDOCUMENTED */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record EmitVibration(
            @JsonProperty("vibration") @Nullable String vibration
        ) {
        }
    
        /* Queues a command to be executed. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record QueueCommand(
            /* The command to execute. */
            @JsonProperty("command") @Nullable Object command
        ) {
        }
}
