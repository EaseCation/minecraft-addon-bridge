package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the amount of experience rewarded when the entity dies or is successfully bred. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ExperienceReward(
    /* A molang expression defining the amount of experience rewarded when this entity is successfully bred. An array of expressions adds each expression's result together for a final total. */
    @JsonProperty("on_bred") @Nullable Object onBred,
    /* A molang expression defining the amount of experience rewarded when this entity dies. An array of expressions adds each expression's result together for a final total. */
    @JsonProperty("on_death") @Nullable Object onDeath
) {
}
