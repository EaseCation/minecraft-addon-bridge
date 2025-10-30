package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the behavior of the entity when the rail gets activated or deactivated. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RailSensor(
    /* If true, on tick this entity will trigger its on_deactivate behavior. */
    @JsonProperty("check_block_types") @Nullable Boolean checkBlockTypes,
    /* If true, this entity will eject all of its riders when it passes over an activated rail. */
    @JsonProperty("eject_on_activate") @Nullable Boolean ejectOnActivate,
    /* If true, this entity will eject all of its riders when it passes over a deactivated rail. */
    @JsonProperty("eject_on_deactivate") @Nullable Boolean ejectOnDeactivate,
    /* Event to call when the rail is activated. */
    @JsonProperty("on_activate") @Nullable Event onActivate,
    /* Event to call when the rail is deactivated. */
    @JsonProperty("on_deactivate") @Nullable Event onDeactivate,
    /* If true, command blocks will start ticking when passing over an activated rail. */
    @JsonProperty("tick_command_block_on_activate") @Nullable Boolean tickCommandBlockOnActivate,
    /* If false, command blocks will stop ticking when passing over a deactivated rail. */
    @JsonProperty("tick_command_block_on_deactivate") @Nullable Boolean tickCommandBlockOnDeactivate
) {
}
