package net.easecation.bridge.core.dto.recipe.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Represents a shapeless crafting recipe.. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ShapelessRecipe1120(
    @JsonProperty("description") Definition description,
    @JsonProperty("tags") @Nullable Tags tags,
    @JsonProperty("unlock") @Nullable Unlock unlock,
    /* Items used as input (without a shape) for the recipe. */
    @JsonProperty("ingredients") @Nullable List<Item> ingredients,
    /* UNDOCUMENTED. */
    @JsonProperty("group") @Nullable String group,
    /* Item used as output for the furnace recipe. */
    @JsonProperty("priority") @Nullable Integer priority,
    /* When input items match the pattern then these items are the result. */
    @JsonProperty("result") @Nullable Object result
) {
}
