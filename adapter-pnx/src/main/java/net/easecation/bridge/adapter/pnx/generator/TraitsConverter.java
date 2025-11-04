package net.easecation.bridge.adapter.pnx.generator;

import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.dto.block.v1_21_60.PlacementDirection;
import net.easecation.bridge.core.dto.block.v1_21_60.PlacementPosition;
import net.easecation.bridge.core.dto.block.v1_21_60.StatesValue;
import net.easecation.bridge.core.dto.block.v1_21_60.Traits;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converter for Bedrock addon traits to states.
 * Traits like minecraft:placement_direction automatically add states to the block.
 */
public class TraitsConverter {

    /**
     * Merge traits definitions into states map.
     * Traits will be converted to their corresponding states.
     *
     * @param existingStates Existing states from BlockDef.description.states (can be null)
     * @param traits Traits from BlockDef.description.traits (can be null)
     * @return Merged states map
     */
    public static Map<String, StatesValue> mergeTraitsIntoStates(
            Map<String, StatesValue> existingStates,
            Traits traits) {

        // Start with existing states or empty map
        Map<String, StatesValue> merged = new HashMap<>(existingStates != null ? existingStates : Map.of());

        if (traits == null) {
            return merged;
        }

        // Process minecraft:placement_direction trait
        if (traits.minecraft_placementDirection() != null) {
            processPlacementDirection(merged, traits.minecraft_placementDirection());
        }

        // Process minecraft:placement_position trait
        if (traits.minecraft_placementPosition() != null) {
            processPlacementPosition(merged, traits.minecraft_placementPosition());
        }

        return merged;
    }

    /**
     * Process minecraft:placement_direction trait.
     * This trait adds direction-related states based on enabled_states.
     */
    private static void processPlacementDirection(
            Map<String, StatesValue> states,
            PlacementDirection placementDirection) {

        List<String> enabledStates = placementDirection.enabledStates();
        if (enabledStates == null || enabledStates.isEmpty()) {
            BridgeLoggerHolder.getLogger().warning("[TraitsConverter] minecraft:placement_direction has no enabled_states");
            return;
        }

        for (String stateName : enabledStates) {
            switch (stateName.toLowerCase()) {
                case "minecraft:cardinal_direction" -> {
                    // 4 horizontal directions: north, south, east, west
                    states.putIfAbsent("minecraft:cardinal_direction",
                        new StatesValue.StatesValue_Variant0(List.of("north", "south", "east", "west")));
                    BridgeLoggerHolder.getLogger().debug("[TraitsConverter] Added minecraft:cardinal_direction state");
                }
                case "minecraft:facing_direction" -> {
                    // 6 directions: down, up, north, south, east, west
                    states.putIfAbsent("minecraft:facing_direction",
                        new StatesValue.StatesValue_Variant0(List.of("down", "up", "north", "south", "east", "west")));
                    BridgeLoggerHolder.getLogger().debug("[TraitsConverter] Added minecraft:facing_direction state");
                }
                case "minecraft:block_face" -> {
                    // Same as facing_direction
                    states.putIfAbsent("minecraft:block_face",
                        new StatesValue.StatesValue_Variant0(List.of("down", "up", "north", "south", "east", "west")));
                    BridgeLoggerHolder.getLogger().debug("[TraitsConverter] Added minecraft:block_face state");
                }
                default -> BridgeLoggerHolder.getLogger().warning("[TraitsConverter] Unknown placement_direction state: " + stateName);
            }
        }
    }

    /**
     * Process minecraft:placement_position trait.
     * This trait adds position-related states based on enabled_states.
     */
    private static void processPlacementPosition(
            Map<String, StatesValue> states,
            PlacementPosition placementPosition) {

        List<String> enabledStates = placementPosition.enabledStates();
        if (enabledStates == null || enabledStates.isEmpty()) {
            BridgeLoggerHolder.getLogger().warning("[TraitsConverter] minecraft:placement_position has no enabled_states");
            return;
        }

        for (String stateName : enabledStates) {
            switch (stateName.toLowerCase()) {
                case "minecraft:block_face" -> {
                    // 6 directions
                    states.putIfAbsent("minecraft:block_face",
                        new StatesValue.StatesValue_Variant0(List.of("down", "up", "north", "south", "east", "west")));
                    BridgeLoggerHolder.getLogger().debug("[TraitsConverter] Added minecraft:block_face state");
                }
                case "minecraft:vertical_half" -> {
                    // top or bottom
                    states.putIfAbsent("minecraft:vertical_half",
                        new StatesValue.StatesValue_Variant0(List.of("bottom", "top")));
                    BridgeLoggerHolder.getLogger().debug("[TraitsConverter] Added minecraft:vertical_half state");
                }
                default -> BridgeLoggerHolder.getLogger().warning("[TraitsConverter] Unknown placement_position state: " + stateName);
            }
        }
    }
}
