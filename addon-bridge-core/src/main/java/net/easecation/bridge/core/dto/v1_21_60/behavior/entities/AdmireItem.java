package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Enables the mob to admire items that have been configured as admirable. Must be used in combination with the admire_item component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AdmireItem(
    @JsonProperty("priority") @Nullable Priority priority,
    /* The sound event to play when admiring the item. */
    @JsonProperty("admire_item_sound") @Nullable SoundEvent admireItemSound,
    /* The event to run when admiring the item. */
    @JsonProperty("on_admire_item_start") @Nullable Event onAdmireItemStart,
    /* The event to run when no longer admiring the item. */
    @JsonProperty("on_admire_item_stop") @Nullable Event onAdmireItemStop,
    /* The range of time in seconds to randomly wait before playing the sound again. */
    @JsonProperty("sound_interval") @Nullable Object soundInterval
) {
}
