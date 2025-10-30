package net.easecation.bridge.core.dto.recipe.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Minecraft recipe 1.12.0 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Recipe(
    /* A version that tells minecraft what type of data format can be expected when reading this file. */
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:recipe_furnace") @Nullable FurnaceRecipe1120 minecraft_recipeFurnace,
    @JsonProperty("minecraft:recipe_brewing_container") @Nullable BrewingRecipe1120 minecraft_recipeBrewingContainer,
    @JsonProperty("minecraft:recipe_brewing_mix") @Nullable BrewingRecipe1120 minecraft_recipeBrewingMix,
    @JsonProperty("minecraft:recipe_shaped") @Nullable ShapedRecipe1120 minecraft_recipeShaped,
    @JsonProperty("minecraft:recipe_shapeless") @Nullable ShapelessRecipe1120 minecraft_recipeShapeless
) {
}
