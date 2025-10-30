package net.easecation.bridge.core.dto.block.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Component(
    @JsonProperty("minecraft:collision_box") @Nullable CollisionBox minecraft_collisionBox,
    @JsonProperty("minecraft:crafting_table") @Nullable CraftingTable minecraft_craftingTable,
    @JsonProperty("minecraft:destructible_by_explosion") @Nullable DestructibleByExplosion minecraft_destructibleByExplosion,
    @JsonProperty("minecraft:destructible_by_mining") @Nullable DestructibleByMining minecraft_destructibleByMining,
    @JsonProperty("minecraft:display_name") @Nullable String minecraft_displayName,
    @JsonProperty("minecraft:flammable") @Nullable Flammable minecraft_flammable,
    @JsonProperty("minecraft:friction") @Nullable Double minecraft_friction,
    @JsonProperty("minecraft:geometry") @Nullable Geometry minecraft_geometry,
    @JsonProperty("minecraft:item_visual") @Nullable ItemVisual minecraft_itemVisual,
    @JsonProperty("minecraft:light_dampening") @Nullable Integer minecraft_lightDampening,
    @JsonProperty("minecraft:light_emission") @Nullable Integer minecraft_lightEmission,
    @JsonProperty("minecraft:liquid_detection") @Nullable LiquidDetection minecraft_liquidDetection,
    @JsonProperty("minecraft:loot") @Nullable String minecraft_loot,
    @JsonProperty("minecraft:map_color") @Nullable MapColor minecraft_mapColor,
    @JsonProperty("minecraft:material_instances") @Nullable Map<String, MaterialInstancesValue> minecraft_materialInstances,
    @JsonProperty("minecraft:placement_filter") @Nullable PlacementFilter minecraft_placementFilter,
    @JsonProperty("minecraft:redstone_conductivity") @Nullable RedstoneConductivity minecraft_redstoneConductivity,
    @JsonProperty("minecraft:selection_box") @Nullable SelectionBox minecraft_selectionBox,
    @JsonProperty("minecraft:transformation") @Nullable Transformation minecraft_transformation,
    @JsonProperty("minecraft:custom_components") @Nullable CustomComponents minecraft_customComponents,
    @JsonProperty("minecraft:tick") @Nullable Tick minecraft_tick,
    @JsonProperty("minecraft:entity_fall_on") @Nullable EntityFallOn minecraft_entityFallOn,
    @JsonProperty("tag:minecraft:diamond_tier_destructible") @Nullable Object tag_minecraft_diamondTierDestructible,
    @JsonProperty("tag:minecraft:iron_tier_destructible") @Nullable Object tag_minecraft_ironTierDestructible,
    @JsonProperty("tag:minecraft:is_axe_item_destructible") @Nullable Object tag_minecraft_isAxeItemDestructible,
    @JsonProperty("tag:minecraft:is_hoe_item_destructible") @Nullable Object tag_minecraft_isHoeItemDestructible,
    @JsonProperty("tag:minecraft:is_mace_item_destructible") @Nullable Object tag_minecraft_isMaceItemDestructible,
    @JsonProperty("tag:minecraft:is_pickaxe_item_destructible") @Nullable Object tag_minecraft_isPickaxeItemDestructible,
    @JsonProperty("tag:minecraft:is_shears_item_destructible") @Nullable Object tag_minecraft_isShearsItemDestructible,
    @JsonProperty("tag:minecraft:is_shovel_item_destructible") @Nullable Object tag_minecraft_isShovelItemDestructible,
    @JsonProperty("tag:minecraft:is_sword_item_destructible") @Nullable Object tag_minecraft_isSwordItemDestructible,
    @JsonProperty("tag:minecraft:netherite_tier_destructible") @Nullable Object tag_minecraft_netheriteTierDestructible,
    @JsonProperty("tag:minecraft:stone_tier_destructible") @Nullable Object tag_minecraft_stoneTierDestructible
) {
}
