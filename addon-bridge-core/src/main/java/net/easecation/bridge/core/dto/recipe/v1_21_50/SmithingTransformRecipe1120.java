package net.easecation.bridge.core.dto.recipe.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Represents a smithing table crafting recipe.. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SmithingTransformRecipe1120(
    @JsonProperty("description") Definition description,
    @JsonProperty("tags") @Nullable Tags tags,
    @JsonProperty("unlock") @Nullable Unlock unlock,
    /* Item used as base for the smithing recipe. */
    @JsonProperty("base") @Nullable Object base,
    /* Item used as addition for the smithing recipe. */
    @JsonProperty("addition") @Nullable Object addition,
    /* When input items match the pattern then these items are the result. */
    @JsonProperty("result") @Nullable Object result
) {
}
