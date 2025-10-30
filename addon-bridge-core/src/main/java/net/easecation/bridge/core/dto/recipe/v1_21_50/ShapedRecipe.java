package net.easecation.bridge.core.dto.recipe.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* Represents a shaped crafting recipe for a crafting table. The key used in the pattern may be any single character except the {@code space} character, which is reserved for empty slots in a recipe.. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ShapedRecipe(
    @JsonProperty("description") Definition description,
    @JsonProperty("tags") Tags tags,
    @JsonProperty("unlock") @Nullable Unlock unlock,
    /* Patten key character mapped to item names. */
    @JsonProperty("key") @Nullable Map<String, Item> key,
    /* UNDOCUMENTED. */
    @JsonProperty("group") @Nullable String group,
    /* Characters that represent a pattern to be defined by keys. */
    @JsonProperty("pattern") @Nullable List<String> pattern,
    /* Item used as output for the furnace recipe. */
    @JsonProperty("priority") @Nullable Integer priority,
    /* When input items match the pattern then these items are the result. */
    @JsonProperty("result") @Nullable Object result,
    /* Used to automatically assume a symmetrical recipe should return the same resultUsed to automatically assume a symmetrical recipe should return the same result */
    @JsonProperty("assume_symmetry") @Nullable Boolean assumeSymmetry
) {
}
