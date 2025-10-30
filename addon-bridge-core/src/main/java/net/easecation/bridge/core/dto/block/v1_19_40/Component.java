package net.easecation.bridge.core.dto.block.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Component(
    @JsonProperty("minecraft:block_light_filter") @Nullable Integer minecraft_blockLightFilter,
    @JsonProperty("minecraft:block_light_emission") @Nullable Double minecraft_blockLightEmission,
    @JsonProperty("minecraft:breakonpush") @Nullable Boolean minecraft_breakonpush,
    @JsonProperty("minecraft:breathability") @Nullable String minecraft_breathability,
    @JsonProperty("minecraft:destroy_time") @Nullable Double minecraft_destroyTime,
    @JsonProperty("minecraft:display_name") @Nullable String minecraft_displayName,
    @JsonProperty("minecraft:entity_collision") @Nullable EntityCollision minecraft_entityCollision,
    @JsonProperty("minecraft:explosion_resistance") @Nullable Double minecraft_explosionResistance,
    @JsonProperty("minecraft:flammable") @Nullable Flammable minecraft_flammable,
    @JsonProperty("minecraft:friction") @Nullable Double minecraft_friction,
    @JsonProperty("minecraft:geometry") @Nullable String minecraft_geometry,
    @JsonProperty("minecraft:immovable") @Nullable Boolean minecraft_immovable,
    @JsonProperty("minecraft:loot") @Nullable String minecraft_loot,
    @JsonProperty("minecraft:map_color") @Nullable String minecraft_mapColor,
    @JsonProperty("minecraft:material_instances") @Nullable MaterialInstances minecraft_materialInstances,
    @JsonProperty("minecraft:onlypistonpush") @Nullable Boolean minecraft_onlypistonpush,
    @JsonProperty("minecraft:part_visibility") @Nullable Map<String, String> minecraft_partVisibility,
    @JsonProperty("minecraft:on_fall_on") @Nullable OnFallOn minecraft_onFallOn,
    @JsonProperty("minecraft:on_interact") @Nullable OnFallOn minecraft_onInteract,
    @JsonProperty("minecraft:on_placed") @Nullable OnPlaced minecraft_onPlaced,
    @JsonProperty("minecraft:on_player_destroyed") @Nullable OnPlayerDestroyed minecraft_onPlayerDestroyed,
    @JsonProperty("minecraft:on_player_placing") @Nullable OnPlayerPlacing minecraft_onPlayerPlacing,
    @JsonProperty("minecraft:on_step_off") @Nullable OnStepOff minecraft_onStepOff,
    @JsonProperty("minecraft:on_step_on") @Nullable OnStepOn minecraft_onStepOn,
    @JsonProperty("minecraft:pick_collision") @Nullable PickCollision minecraft_pickCollision,
    @JsonProperty("minecraft:placement_filter") @Nullable PlacementFilter minecraft_placementFilter,
    @JsonProperty("minecraft:preventsjumping") @Nullable Boolean minecraft_preventsjumping,
    @JsonProperty("minecraft:random_ticking") @Nullable RandomTicking minecraft_randomTicking,
    @JsonProperty("minecraft:rotation") @Nullable Rotation minecraft_rotation,
    @JsonProperty("minecraft:ticking") @Nullable Ticking minecraft_ticking,
    @JsonProperty("minecraft:unit_cube") @Nullable net.easecation.bridge.core.dto.EmptyObject minecraft_unitCube,
    @JsonProperty("minecraft:unwalkable") @Nullable Boolean minecraft_unwalkable
) {
}
