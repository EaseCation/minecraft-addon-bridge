package net.easecation.bridge.adapter.easecation.block;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Connection;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.PlacementDirection;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.PlacementPosition;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Traits;

/**
 * Utility class for converting Traits DTO to NBT format.
 * Based on ECProEntity's BlockTraits.toNBT() implementation.
 *
 * Traits provide shortcuts for using vanilla block states without needing
 * to define and manage events or triggers on custom blocks.
 */
public class BlockTraitsNBT {

    /**
     * Convert Traits to NBT ListTag for block registration.
     *
     * @param traits The traits data from behavior pack
     * @return ListTag containing all trait NBT data
     */
    public static ListTag<CompoundTag> toNBT(Traits traits) {
        if (traits == null) {
            return new ListTag<>("traits");
        }

        ListTag<CompoundTag> listTag = new ListTag<>("traits");

        // Placement Direction Trait
        if (traits.minecraft_placementDirection() != null) {
            listTag.add(convertPlacementDirection(traits.minecraft_placementDirection()));
        }

        // Placement Position Trait
        if (traits.minecraft_placementPosition() != null) {
            listTag.add(convertPlacementPosition(traits.minecraft_placementPosition()));
        }

        // Connection Trait (for fence-like blocks)
        // Note: minecraft:connection trait not yet in DTO schema
        // If needed, uncomment and implement when added to schema:
        // if (traits.minecraft_connection() != null) {
        //     listTag.add(convertConnection(traits.minecraft_connection()));
        // }

        return listTag;
    }

    /**
     * Convert PlacementDirection trait to NBT.
     *
     * This trait contains information about the player's rotation when the block was placed.
     * Supported states:
     * - minecraft:cardinal_direction: north, south, east, west (4 directions)
     * - minecraft:facing_direction: down, up, north, south, east, west (6 directions)
     *
     * @param direction The placement direction configuration
     * @return CompoundTag for placement_direction trait
     */
    private static CompoundTag convertPlacementDirection(PlacementDirection direction) {
        CompoundTag tag = new CompoundTag();

        tag.putString("name", "minecraft:placement_direction");

        // Enabled states
        CompoundTag enabledStates = new CompoundTag();

        boolean hasCardinalDirection = direction.enabledStates() != null &&
            direction.enabledStates().contains("minecraft:cardinal_direction");
        boolean hasFacingDirection = direction.enabledStates() != null &&
            direction.enabledStates().contains("minecraft:facing_direction");

        enabledStates.putBoolean("cardinal_direction", hasCardinalDirection);
        enabledStates.putBoolean("facing_direction", hasFacingDirection);

        tag.putCompound("enabled_states", enabledStates);

        // Y rotation offset (only applies to horizontal state values)
        if (direction.yRotationOffset() != null) {
            tag.putFloat("y_rotation_offset", direction.yRotationOffset().floatValue());
        } else {
            tag.putFloat("y_rotation_offset", 0.0f);
        }

        return tag;
    }

    /**
     * Convert PlacementPosition trait to NBT.
     *
     * This trait contains information about where the player placed the block.
     * Supported states:
     * - minecraft:block_face: down, up, north, south, east, west (which face was clicked)
     * - minecraft:vertical_half: bottom, top (for slabs, stairs, etc.)
     *
     * @param position The placement position configuration
     * @return CompoundTag for placement_position trait
     */
    private static CompoundTag convertPlacementPosition(PlacementPosition position) {
        CompoundTag tag = new CompoundTag();

        tag.putString("name", "minecraft:placement_position");

        // Enabled states
        CompoundTag enabledStates = new CompoundTag();

        boolean hasBlockFace = position.enabledStates() != null &&
            position.enabledStates().contains("minecraft:block_face");
        boolean hasVerticalHalf = position.enabledStates() != null &&
            position.enabledStates().contains("minecraft:vertical_half");

        enabledStates.putBoolean("block_face", hasBlockFace);
        enabledStates.putBoolean("vertical_half", hasVerticalHalf);

        tag.putCompound("enabled_states", enabledStates);

        return tag;
    }

    /**
     * Convert Connection trait to NBT.
     *
     * This trait creates cardinal connection states for fence-like blocks that connect to adjacent blocks.
     * Supported states:
     * - minecraft:cardinal_connections: creates 4 boolean states (north, south, east, west)
     *
     * This creates block states:
     * - north_connection: boolean
     * - south_connection: boolean
     * - east_connection: boolean
     * - west_connection: boolean
     *
     * @param connection The connection configuration
     * @return CompoundTag for connection trait
     *
     * Note: Currently commented out because minecraft:connection is not in the DTO schema.
     * Uncomment when Connection class is added to the DTO.
     */
    /*
    private static CompoundTag convertConnection(Connection connection) {
        CompoundTag tag = new CompoundTag();

        tag.putString("name", "minecraft:connection");

        // Enabled states
        CompoundTag enabledStates = new CompoundTag();

        boolean hasCardinalConnections = connection.enabledStates() != null &&
            connection.enabledStates().contains("minecraft:cardinal_connections");

        enabledStates.putBoolean("cardinal_connections", hasCardinalConnections);

        tag.putCompound("enabled_states", enabledStates);

        return tag;
    }
    */
}
