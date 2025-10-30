package net.easecation.bridge.core.dto.recipe.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Represents a furnace recipe for a furnace.'Input{@code  items will burn and transform into items specified in }output`.. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FurnaceRecipe(
    @JsonProperty("description") Definition description,
    @JsonProperty("tags") @Nullable Tags tags,
    /* Items used as input for the furnace recipe. */
    @JsonProperty("input") @Nullable String input,
    /* Items used as output for the furnace recipe. */
    @JsonProperty("output") @Nullable String output,
    @JsonProperty("unlock") @Nullable Unlock unlock
) {
}
