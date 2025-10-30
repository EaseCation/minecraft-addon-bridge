package net.easecation.bridge.core.dto.block.v1_20_10;

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
    @JsonProperty("minecraft:geometry") @Nullable String minecraft_geometry,
    @JsonProperty("minecraft:light_dampening") @Nullable Integer minecraft_lightDampening,
    @JsonProperty("minecraft:light_emission") @Nullable Integer minecraft_lightEmission,
    @JsonProperty("minecraft:loot") @Nullable String minecraft_loot,
    @JsonProperty("minecraft:map_color") @Nullable MapColor minecraft_mapColor,
    @JsonProperty("minecraft:material_instances") @Nullable Map<String, MaterialInstancesValue> minecraft_materialInstances,
    @JsonProperty("minecraft:part_visibility") @Nullable Map<String, Object> minecraft_partVisibility,
    @JsonProperty("minecraft:placement_filter") @Nullable PlacementFilter minecraft_placementFilter,
    @JsonProperty("minecraft:rotation") @Nullable Rotation minecraft_rotation,
    @JsonProperty("minecraft:selection_box") @Nullable SelectionBox minecraft_selectionBox,
    @JsonProperty("minecraft:transformation") @Nullable Transformation minecraft_transformation,
    @JsonProperty("minecraft:unit_cube") @Nullable net.easecation.bridge.core.dto.EmptyObject minecraft_unitCube,
    @JsonProperty("minecraft:on_fall_on") @Nullable OnFallOn minecraft_onFallOn,
    @JsonProperty("minecraft:on_interact") @Nullable OnFallOn minecraft_onInteract,
    @JsonProperty("minecraft:on_placed") @Nullable OnPlaced minecraft_onPlaced,
    @JsonProperty("minecraft:on_player_destroyed") @Nullable OnPlayerDestroyed minecraft_onPlayerDestroyed,
    @JsonProperty("minecraft:on_player_placing") @Nullable OnPlayerPlacing minecraft_onPlayerPlacing,
    @JsonProperty("minecraft:on_step_off") @Nullable OnStepOff minecraft_onStepOff,
    @JsonProperty("minecraft:on_step_on") @Nullable OnStepOn minecraft_onStepOn,
    @JsonProperty("minecraft:queued_ticking") @Nullable RandomTicking minecraft_queuedTicking,
    @JsonProperty("minecraft:random_ticking") @Nullable RandomTicking minecraft_randomTicking
) {
}
