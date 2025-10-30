package net.easecation.bridge.core.dto.block.v1_21_50;

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
    @JsonProperty("minecraft:light_dampening") @Nullable Integer minecraft_lightDampening,
    @JsonProperty("minecraft:light_emission") @Nullable Integer minecraft_lightEmission,
    @JsonProperty("minecraft:loot") @Nullable String minecraft_loot,
    @JsonProperty("minecraft:map_color") @Nullable MapColor minecraft_mapColor,
    @JsonProperty("minecraft:material_instances") @Nullable Map<String, MaterialInstancesValue> minecraft_materialInstances,
    @JsonProperty("minecraft:placement_filter") @Nullable PlacementFilter minecraft_placementFilter,
    @JsonProperty("minecraft:redstone_conductivity") @Nullable RedstoneConductivity minecraft_redstoneConductivity,
    @JsonProperty("minecraft:selection_box") @Nullable SelectionBox minecraft_selectionBox,
    @JsonProperty("minecraft:transformation") @Nullable Transformation minecraft_transformation,
    @JsonProperty("minecraft:custom_components") @Nullable CustomComponents minecraft_customComponents,
    @JsonProperty("minecraft:tick") @Nullable Tick minecraft_tick,
    @JsonProperty("minecraft:entity_fall_on") @Nullable EntityFallOn minecraft_entityFallOn
) {
}
