package net.easecation.bridge.core.dto.recipe.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecipesDefinition(
    @JsonProperty("format_version") @Nullable String formatVersion
) {
}
