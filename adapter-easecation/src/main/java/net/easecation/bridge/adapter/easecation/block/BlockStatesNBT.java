package net.easecation.bridge.adapter.easecation.block;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.IntTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.StringTag;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.StatesValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class for converting block states (Map<String, Object>) to NBT format.
 * Based on ECProEntity's BlockStates.toNBT() implementation.
 *
 * Block states are variables that can be set to different values to change
 * how a block looks or behaves.
 *
 * Supported state types:
 * - Boolean: [false, true]
 * - Integer: [0, 1, 2, ...] or range {min: 0, max: 15}
 * - String (Enum): ["value1", "value2", "value3"]
 */
public class BlockStatesNBT {

    /**
     * Convert states map (with StatesValue DTO) to NBT properties ListTag for block registration.
     * This is the new method that handles the structured StatesValue type from JSON Schema.
     *
     * @param statesMap Map of state name to StatesValue (from BlockDefinitions.Description.states())
     * @return ListTag containing all state property NBT data
     */
    public static ListTag<CompoundTag> toPropertiesNBT(Map<String, StatesValue> statesMap) {
        ListTag<CompoundTag> properties = new ListTag<>();

        if (statesMap == null || statesMap.isEmpty()) {
            return properties;
        }

        for (Map.Entry<String, StatesValue> entry : statesMap.entrySet()) {
            String stateName = entry.getKey();
            StatesValue stateValue = entry.getValue();

            try {
                CompoundTag property = convertStatesValueToProperty(stateName, stateValue);
                if (property != null) {
                    properties.add(property);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(
                    "Failed to convert state '" + stateName + "': " + e.getMessage(), e);
            }
        }

        return properties;
    }

    /**
     * Convert a StatesValue to property NBT.
     * Handles both enum list format (Variant0) and integer range format (Variant1).
     *
     * @param name State name (e.g., "my:color")
     * @param statesValue StatesValue from DTO (sealed interface with two variants)
     * @return CompoundTag for the property, or null if invalid
     */
    private static CompoundTag convertStatesValueToProperty(String name, StatesValue statesValue) {
        if (statesValue instanceof StatesValue.StatesValue_Variant0 variant0) {
            // Enum list format: ["red", "green", "blue"] or [0, 1, 2, 3]
            List<Object> values = variant0.value();
            if (values == null || values.isEmpty()) {
                throw new IllegalArgumentException("State '" + name + "' has empty value list");
            }
            return convertStateToProperty(name, values);

        } else if (statesValue instanceof StatesValue.StatesValue_Variant1 variant1) {
            // Integer range format: {min: 0, max: 15}
            StatesValue.StatesValue_Variant1.Values rangeValues = variant1.values();
            if (rangeValues == null) {
                throw new IllegalArgumentException("State '" + name + "' has null range values");
            }

            Integer min = rangeValues.min();
            Integer max = rangeValues.max();

            if (min == null || max == null) {
                throw new IllegalArgumentException(
                    "State '" + name + "' range must have both min and max values");
            }

            // Convert range to list of integers
            List<Object> values = new ArrayList<>();
            for (int i = min; i <= max; i++) {
                values.add(i);
            }

            return convertStateToProperty(name, values);

        } else {
            throw new IllegalArgumentException(
                "Unknown StatesValue variant for '" + name + "': " + statesValue.getClass());
        }
    }

    /**
     * Convert states map to NBT properties ListTag for block registration.
     * This is the legacy method that accepts raw Map<String, Object>.
     *
     * Format in JSON:
     * {
     *   "states": {
     *     "my:color": ["red", "green", "blue"],           // String enum
     *     "my:powered": [false, true],                     // Boolean
     *     "my:level": [0, 1, 2, 3, 4]                      // Integer
     *   }
     * }
     *
     * @param states Map of state name to state values (must be List)
     * @return ListTag containing all state property NBT data
     * @deprecated Use {@link #toPropertiesNBT(Map)} with StatesValue type instead
     */
    @Deprecated
    public static ListTag<CompoundTag> toPropertiesNBTLegacy(Map<String, Object> states) {
        ListTag<CompoundTag> properties = new ListTag<>();

        if (states == null || states.isEmpty()) {
            return properties;
        }

        for (Map.Entry<String, Object> entry : states.entrySet()) {
            String stateName = entry.getKey();
            Object stateValue = entry.getValue();

            try {
                CompoundTag property = convertStateToProperty(stateName, stateValue);
                if (property != null) {
                    properties.add(property);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(
                    "Failed to convert state '" + stateName + "': " + e.getMessage(), e);
            }
        }

        return properties;
    }

    /**
     * Convert a single state to property NBT.
     *
     * @param name State name (e.g., "my:color")
     * @param value State value (List of values or Map for integer range)
     * @return CompoundTag for the property, or null if invalid
     */
    private static CompoundTag convertStateToProperty(String name, Object value) {
        if (!(value instanceof List<?> values)) {
            throw new IllegalArgumentException("State value must be a List, got: " + value.getClass());
        }

        if (values.size() < 2) {
            throw new IllegalArgumentException(
                "State variation count must be at least 2 for '" + name + "'");
        }

        CompoundTag property = new CompoundTag();
        property.putString("name", name);

        // Determine state type from first value
        Object firstValue = values.get(0);

        if (firstValue instanceof Boolean) {
            // Boolean state: [false, true]
            property.putList("enum", convertBooleanEnum());
        } else if (firstValue instanceof Number) {
            // Integer state: [0, 1, 2, ...]
            property.putList("enum", convertIntegerEnum(values));
        } else if (firstValue instanceof String) {
            // String enum state: ["value1", "value2", ...]
            property.putList("enum", convertStringEnum(values));
        } else {
            throw new IllegalArgumentException(
                "Unknown state type for '" + name + "': " + firstValue.getClass());
        }

        return property;
    }

    /**
     * Convert boolean state to enum list.
     * Always returns [false, true].
     */
    private static ListTag<?> convertBooleanEnum() {
        ListTag<CompoundTag> enumList = new ListTag<>();
        enumList.add(new CompoundTag().putBoolean("", false));
        enumList.add(new CompoundTag().putBoolean("", true));
        return enumList;
    }

    /**
     * Convert integer state to enum list.
     * Extracts all integer values, deduplicates, sorts, and creates range from min to max.
     *
     * Note: Current implementation requires min value to be 0.
     * Non-zero min values are not fully supported by EaseCation Nukkit.
     *
     * @param values List of Number objects
     * @return ListTag of integers from min to max
     */
    private static ListTag<IntTag> convertIntegerEnum(List<?> values) {
        // Extract and sort unique integer values
        List<Integer> intValues = values.stream()
            .filter(v -> v instanceof Number)
            .map(v -> ((Number) v).intValue())
            .distinct()
            .sorted()
            .toList();

        if (intValues.isEmpty()) {
            throw new IllegalArgumentException("Integer state has no valid values");
        }

        int min = intValues.get(0);
        int max = intValues.get(intValues.size() - 1);

        // Validate range
        if (min < 0) {
            throw new IllegalArgumentException("Integer state value cannot be negative: " + min);
        }

        if (max <= min) {
            throw new IllegalArgumentException(
                "Integer state maxValue must be greater than minValue: " + min + " >= " + max);
        }

        // Note: EaseCation Nukkit currently requires min to be 0
        if (min > 0) {
            // Log warning (would need logger injection)
            // For now, we'll enforce this restriction
            throw new IllegalArgumentException(
                "Integer state with non-zero min value is not fully supported: min=" + min +
                ". Please start your range from 0.");
        }

        // Generate enum list from min to max
        ListTag<IntTag> enumList = new ListTag<>();
        for (int i = min; i <= max; i++) {
            enumList.add(new IntTag("", i));
        }

        return enumList;
    }

    /**
     * Convert string enum state to enum list.
     * Deduplicates values and maintains order.
     *
     * @param values List of String objects
     * @return ListTag of string values
     */
    private static ListTag<StringTag> convertStringEnum(List<?> values) {
        // Extract and deduplicate string values (maintain order)
        List<String> stringValues = values.stream()
            .filter(v -> v instanceof String)
            .map(v -> (String) v)
            .distinct()
            .toList();

        if (stringValues.isEmpty()) {
            throw new IllegalArgumentException("String enum state has no valid values");
        }

        // Generate enum list
        ListTag<StringTag> enumList = new ListTag<>();
        for (String value : stringValues) {
            enumList.add(new StringTag("", value));
        }

        return enumList;
    }
}
