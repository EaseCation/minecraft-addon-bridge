package net.easecation.bridge.core.dto.v1_21_60.behavior.recipes;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Minecraft recipe */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RecipesDefinition(
    /* A version that tells minecraft what type of data format can be expected when reading this file. */
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:recipe_furnace") @Nullable FurnaceRecipe minecraft_recipeFurnace,
    @JsonProperty("minecraft:recipe_brewing_container") @Nullable BrewingRecipe1120 minecraft_recipeBrewingContainer,
    @JsonProperty("minecraft:recipe_brewing_mix") @Nullable BrewingRecipe1120 minecraft_recipeBrewingMix,
    @JsonProperty("minecraft:recipe_shaped") @Nullable ShapedRecipe minecraft_recipeShaped,
    @JsonProperty("minecraft:recipe_shapeless") @Nullable ShapelessRecipe1120 minecraft_recipeShapeless,
    @JsonProperty("minecraft:recipe_smithing_transform") @Nullable SmithingTransformRecipe1120 minecraft_recipeSmithingTransform
) {
}
