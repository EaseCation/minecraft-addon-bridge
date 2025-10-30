package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows this entity to generate paths by walking around and jumping up and down a block like regular mobs. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NavigationWalk(
    /* Tells the pathfinder to avoid blocks that cause damage when finding a path. */
    @JsonProperty("avoid_damage_blocks") @Nullable Boolean avoidDamageBlocks,
    /* Tells the pathfinder to avoid portals (like nether portals) when finding a path. */
    @JsonProperty("avoid_portals") @Nullable Boolean avoidPortals,
    /* Whether or not the pathfinder should avoid tiles that are exposed to the sun when creating paths. */
    @JsonProperty("avoid_sun") @Nullable Boolean avoidSun,
    /* Tells the pathfinder to avoid water when creating a path. */
    @JsonProperty("avoid_water") @Nullable Boolean avoidWater,
    /* Tells the pathfinder which blocks to avoid when creating a path. */
    @JsonProperty("blocks_to_avoid") @Nullable List<BlockReference> blocksToAvoid,
    /* Tells the pathfinder whether or not it can jump out of water (like a dolphin). */
    @JsonProperty("can_breach") @Nullable Boolean canBreach,
    /* Tells the pathfinder that it can path through a closed door and break it. */
    @JsonProperty("can_break_doors") @Nullable Boolean canBreakDoors,
    /* Tells the pathfinder whether or not it can jump up blocks. */
    @JsonProperty("can_jump") @Nullable Boolean canJump,
    /* Tells the pathfinder whether or not it float. */
    @JsonProperty("can_float") @Nullable Boolean canFloat,
    /* Tells the pathfinder that it can path through a closed door assuming the AI will open the door. */
    @JsonProperty("can_open_doors") @Nullable Boolean canOpenDoors,
    /* Tells the pathfinder that it can path through a closed iron door assuming the AI will open the door. */
    @JsonProperty("can_open_iron_doors") @Nullable Boolean canOpenIronDoors,
    /* Whether a path can be created through a door. */
    @JsonProperty("can_pass_doors") @Nullable Boolean canPassDoors,
    /* Tells the pathfinder that it can start pathing when in the air. */
    @JsonProperty("can_path_from_air") @Nullable Boolean canPathFromAir,
    /* Tells the pathfinder whether or not it can travel on the surface of the lava. */
    @JsonProperty("can_path_over_lava") @Nullable Boolean canPathOverLava,
    /* Tells the pathfinder whether or not it can travel on the surface of the water. */
    @JsonProperty("can_path_over_water") @Nullable Boolean canPathOverWater,
    /* Tells the pathfinder whether or not it will be pulled down by gravity while in water. */
    @JsonProperty("can_sink") @Nullable Boolean canSink,
    /* Tells the pathfinder whether or not it can path anywhere through water and plays swimming animation along that path. */
    @JsonProperty("can_swim") @Nullable Boolean canSwim,
    /* Tells the pathfinder whether or not it can walk on the ground outside water. */
    @JsonProperty("can_walk") @Nullable Boolean canWalk,
    /* Tells the pathfinder whether or not it can travel in lava like walking on ground. */
    @JsonProperty("can_walk_in_lava") @Nullable Boolean canWalkInLava,
    /* Tells the pathfinder whether or not it can walk on the ground underwater. */
    @JsonProperty("is_amphibious") @Nullable Boolean isAmphibious
) {
}
