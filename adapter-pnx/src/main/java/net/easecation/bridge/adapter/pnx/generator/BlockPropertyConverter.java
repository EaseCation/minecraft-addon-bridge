package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.block.property.CommonBlockProperties;
import cn.nukkit.block.property.type.BlockPropertyType;
import cn.nukkit.block.property.type.BooleanPropertyType;
import cn.nukkit.block.property.type.EnumPropertyType;
import cn.nukkit.block.property.type.IntPropertyType;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.dto.block.v1_21_60.StatesValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Converter utility for transforming Bedrock addon states definitions
 * into PowerNukkitX BlockPropertyType instances.
 */
public class BlockPropertyConverter {

    /**
     * Convert a map of states to an array of BlockPropertyType.
     *
     * @param states The states map from BlockDef
     * @return Array of BlockPropertyType instances, empty if no valid states
     */
    public static BlockPropertyType<?>[] convertStates(Map<String, StatesValue> states) {
        if (states == null || states.isEmpty()) {
            return new BlockPropertyType<?>[0];
        }

        List<BlockPropertyType<?>> properties = new ArrayList<>();

        for (Map.Entry<String, StatesValue> entry : states.entrySet()) {
            String name = entry.getKey();
            StatesValue value = entry.getValue();

            BlockPropertyType<?> property = convertSingleState(name, value);
            if (property != null) {
                properties.add(property);
            }
        }

        return properties.toArray(new BlockPropertyType<?>[0]);
    }

    /**
     * Convert a single state definition to BlockPropertyType.
     * Tries to use CommonBlockProperties for well-known properties.
     *
     * @param name  Property name
     * @param value State value definition
     * @return BlockPropertyType instance, or null if conversion fails
     */
    private static BlockPropertyType<?> convertSingleState(String name, StatesValue value) {
        // Try to use CommonBlockProperties for well-known properties
        BlockPropertyType<?> commonProperty = tryGetCommonProperty(name);
        if (commonProperty != null) {
            BridgeLoggerHolder.getLogger().debug("[BlockPropertyConverter] Using CommonBlockProperties." + name);
            return commonProperty;
        }

        // Integer range: {min: 0, max: 15}
        if (value instanceof StatesValue.StatesValue_Variant1 variant1) {
            var values = variant1.values();
            int min = values.min() != null ? values.min() : 0;
            int max = values.max() != null ? values.max() : 15;

            BridgeLoggerHolder.getLogger().debug("[BlockPropertyConverter] Creating IntPropertyType: "
                + name + " [" + min + ".." + max + "]");

            return IntPropertyType.of(name, min, max, min);
        }
        // Value list: [true, false] or ["north", "south", "east", "west"]
        else if (value instanceof StatesValue.StatesValue_Variant0 variant0) {
            List<Object> valuesList = variant0.value();

            if (valuesList == null || valuesList.isEmpty()) {
                BridgeLoggerHolder.getLogger().warning("[BlockPropertyConverter] Empty values list for state: " + name);
                return null;
            }

            Object firstValue = valuesList.get(0);

            // Boolean property
            if (firstValue instanceof Boolean) {
                BridgeLoggerHolder.getLogger().debug("[BlockPropertyConverter] Creating BooleanPropertyType: " + name);
                return BooleanPropertyType.of(name, false);
            }
            // String enum property
            else if (firstValue instanceof String) {
                String[] enumValues = valuesList.stream()
                    .map(Object::toString)
                    .toArray(String[]::new);

                BridgeLoggerHolder.getLogger().debug("[BlockPropertyConverter] Creating EnumPropertyType: "
                    + name + " " + String.join(", ", enumValues));

                // Create a custom enum property type for string values
                // Note: PNX's EnumPropertyType typically expects an Enum class,
                // but for dynamic values we create a string-based property
                return createStringEnumProperty(name, enumValues);
            }
            else {
                BridgeLoggerHolder.getLogger().warning("[BlockPropertyConverter] Unsupported value type for state: "
                    + name + " (type: " + firstValue.getClass().getSimpleName() + ")");
                return null;
            }
        }

        BridgeLoggerHolder.getLogger().warning("[BlockPropertyConverter] Unknown StatesValue variant for: " + name);
        return null;
    }

    /**
     * Try to get a well-known property from CommonBlockProperties.
     *
     * @param name Property name
     * @return CommonBlockProperties instance, or null if not found
     */
    private static BlockPropertyType<?> tryGetCommonProperty(String name) {
        return switch (name.toLowerCase()) {
            // Cardinal directions (4 directions: north, south, east, west)
            case "minecraft:cardinal_direction" -> CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION;
            case "cardinal_direction" -> CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION;

            // Facing direction (6 directions: down, up, north, south, east, west)
            case "minecraft:facing_direction" -> CommonBlockProperties.MINECRAFT_FACING_DIRECTION;
            case "facing_direction" -> CommonBlockProperties.FACING_DIRECTION;

            // Block face
            case "minecraft:block_face" -> CommonBlockProperties.MINECRAFT_BLOCK_FACE;
            case "block_face" -> CommonBlockProperties.MINECRAFT_BLOCK_FACE;

            // Vertical half
            case "minecraft:vertical_half" -> CommonBlockProperties.MINECRAFT_VERTICAL_HALF;
            case "vertical_half" -> CommonBlockProperties.MINECRAFT_VERTICAL_HALF;

            // Common boolean properties
            case "open_bit" -> CommonBlockProperties.OPEN_BIT;
            case "powered_bit" -> CommonBlockProperties.POWERED_BIT;
            case "upper_block_bit" -> CommonBlockProperties.UPPER_BLOCK_BIT;
            case "upside_down_bit" -> CommonBlockProperties.UPSIDE_DOWN_BIT;

            // Common int properties
            case "direction" -> CommonBlockProperties.DIRECTION;
            case "rotation" -> CommonBlockProperties.ROTATION;
            case "ground_sign_direction" -> CommonBlockProperties.GROUND_SIGN_DIRECTION;

            default -> null;
        };
    }

    /**
     * Create a custom string-based enum property.
     * Since PNX expects Enum classes, we use IntPropertyType with value mapping as a workaround.
     *
     * @param name Property name
     * @param values String values array
     * @return IntPropertyType representing the enum
     */
    private static BlockPropertyType<?> createStringEnumProperty(String name, String[] values) {
        // For now, we map string enums to int properties (0..n-1)
        // The actual string values will be handled at the permutation level
        // TODO: Consider creating dynamic enum classes if needed
        int maxIndex = values.length - 1;
        return IntPropertyType.of(name, 0, maxIndex, 0);
    }
}
