package net.easecation.bridge.core.dto.recipe.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Represents a Potion Brewing Container Recipe.. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BrewingRecipe1120(
    @JsonProperty("description") Definition description,
    @JsonProperty("tags") @Nullable Tags tags,
    /* Input potion used on the brewing stand. */
    @JsonProperty("input") @Nullable String input,
    /* Output potion from mixing the input potion with the reagent on the brewing stand. */
    @JsonProperty("output") @Nullable String output,
    /* Item used to mix with the input potion. */
    @JsonProperty("reagent") @Nullable String reagent
) {
}
