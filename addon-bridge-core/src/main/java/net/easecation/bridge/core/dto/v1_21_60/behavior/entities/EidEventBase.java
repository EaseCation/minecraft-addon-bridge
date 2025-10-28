package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EidEventBase(
    @JsonProperty("filters") @Nullable Filters filters,
    /* Triggers additional events. */
    @JsonProperty("trigger") @Nullable Trigger trigger,
    /* What gets added when the event gets triggered. */
    @JsonProperty("add") @Nullable AddOrRemove add,
    /* What gets removed when the event gets triggered. */
    @JsonProperty("remove") @Nullable AddOrRemove remove,
    /* Randomly selects one of the following items based upon their weight and the total weights. */
    @JsonProperty("randomize") @Nullable List<EidWeightedEventBase> randomize,
    /* A series of filters and components to be added. */
    @JsonProperty("sequence") @Nullable List<EidEventBase> sequence,
    /* Allows the entity to execute an event on the block at its home position */
    @JsonProperty("execute_event_on_home_block") @Nullable ExecuteEventOnHomeBlock executeEventOnHomeBlock,
    /* Allows an entity to reset its target. */
    @JsonProperty("reset_target") @Nullable net.easecation.bridge.core.dto.EmptyObject resetTarget,
    /* UNDOCUMENTED */
    @JsonProperty("emit_vibration") @Nullable EmitVibration emitVibration,
    /* Sets a property on the entity. */
    @JsonProperty("set_property") @Nullable Map<String, Object> setProperty,
    /* Queues a command to be executed. */
    @JsonProperty("queue_command") @Nullable QueueCommand queueCommand,
    /* Allows the owner entity to emit sounds */
    @JsonProperty("play_sound") @Nullable PlaySound playSound,
    /* Allowing particles to be emitted at the center of the entity's bounding box */
    @JsonProperty("emit_particle") @Nullable EmitParticle emitParticle,
    /* Stops the entity's movement */
    @JsonProperty("stop_movement") @Nullable StopMovement stopMovement,
    /* Sets the entity's home position to its current position */
    @JsonProperty("set_home_position") @Nullable net.easecation.bridge.core.dto.EmptyObject setHomePosition,
    /* Will evaluate every filter in order and execute the first valid one */
    @JsonProperty("first_valid") @Nullable List<EidEventBase> firstValid
) {
    
        /* Allows the entity to execute an event on the block at its home position */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record ExecuteEventOnHomeBlock(
            /* The event to execute */
            @JsonProperty("event") @Nullable String event
        ) {
        }
    
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
    
        /* Allows the owner entity to emit sounds */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record PlaySound(
            /* Specifies the sound event to play */
            @JsonProperty("sound") @Nullable String sound
        ) {
        }
    
        /* Allowing particles to be emitted at the center of the entity's bounding box */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record EmitParticle(
            /* Specifies the type of particle to emit */
            @JsonProperty("particle") @Nullable String particle
        ) {
        }
    
        /* Stops the entity's movement */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record StopMovement(
            /* Specifies whether vertical movement should be stopped */
            @JsonProperty("stop_vertical_movement") @Nullable Boolean stopVerticalMovement,
            /* Specifies whether horizontal movement should be stopped */
            @JsonProperty("stop_horizontal_movement") @Nullable Boolean stopHorizontalMovement
        ) {
        }
}
