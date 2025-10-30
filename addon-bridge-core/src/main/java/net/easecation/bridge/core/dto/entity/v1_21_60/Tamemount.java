package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the Entity to be tamed by mounting it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Tamemount(
    /* The amount the entity's temper will increase when mounted. */
    @JsonProperty("attempt_temper_mod") @Nullable Integer attemptTemperMod,
    /* The list of items that, if carried while interacting with the entity, will anger it. */
    @JsonProperty("auto_reject_items") @Nullable Object autoRejectItems,
    /* The text that shows in the feeding interact button. */
    @JsonProperty("feed_text") @Nullable String feedText,
    /* The list of items that can be used to increase the entity's temper and speed up the taming process. */
    @JsonProperty("feed_items") @Nullable Object feedItems,
    /* The maximum value for the entity's random starting temper. */
    @JsonProperty("max_temper") @Nullable Integer maxTemper,
    /* The minimum value for the entity's random starting temper. */
    @JsonProperty("min_temper") @Nullable Integer minTemper,
    /* The text that shows in the riding interact button. */
    @JsonProperty("ride_text") @Nullable String rideText,
    /* Event that triggers when the entity becomes tamed. */
    @JsonProperty("tame_event") @Nullable Event tameEvent
) {
}
