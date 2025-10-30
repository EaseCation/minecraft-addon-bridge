package net.easecation.bridge.adapter.mot.block;

import cn.nukkit.block.custom.properties.BlockProperties;
import cn.nukkit.block.custom.properties.BlockProperty;
import cn.nukkit.block.custom.properties.BooleanBlockProperty;
import cn.nukkit.block.custom.properties.IntBlockProperty;
import cn.nukkit.block.custom.properties.EnumBlockProperty;
import net.easecation.bridge.core.dto.block.v1_21_60.StatesValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Builder for MOT BlockProperties from BlockDef states.
 * Converts Bedrock behavior pack block states to MOT's BlockProperty system.
 *
 * TODO: MOT的BlockProperties系统与Bedrock的states存在兼容性问题
 * 1. MOT的variantGenerations机制需要深入研究
 * 2. BlockProperty的meta值计算与Bedrock的states映射不一致
 * 3. 需要参考Lumi等社区插件的实现方式
 * 4. 暂时跳过带states的方块注册，仅支持简单方块
 */
public class BlockPropertiesBuilder {

    /**
     * Build BlockProperties from a map of state definitions.
     *
     * @param states Map of state name to state value definition
     * @return BlockProperties instance, or null if states is null or empty
     */
    public static BlockProperties build(Map<String, StatesValue> states) {
        if (states == null || states.isEmpty()) {
            return null;
        }

        List<BlockProperty<?>> properties = new ArrayList<>();

        for (Map.Entry<String, StatesValue> entry : states.entrySet()) {
            String name = entry.getKey();
            StatesValue value = entry.getValue();

            BlockProperty<?> property = buildProperty(name, value);
            if (property != null) {
                properties.add(property);
            }
        }

        if (properties.isEmpty()) {
            return null;
        }

        return new BlockProperties(properties.toArray(new BlockProperty[0]));
    }

    /**
     * Build a single BlockProperty from a state definition.
     *
     * @param name The property name
     * @param stateValue The state value definition
     * @return BlockProperty instance, or null if cannot be converted
     */
    private static BlockProperty<?> buildProperty(String name, StatesValue stateValue) {
        if (stateValue instanceof StatesValue.StatesValue_Variant1 variant1) {
            // Integer range: use IntBlockProperty
            StatesValue.StatesValue_Variant1.Values values = variant1.values();
            if (values != null && values.min() != null && values.max() != null) {
                return new IntBlockProperty(name, false, values.max(), values.min());
            }
        } else if (stateValue instanceof StatesValue.StatesValue_Variant0 variant0) {
            // List of values: determine type based on first element
            List<Object> valueList = variant0.value();
            if (valueList == null || valueList.isEmpty()) {
                return null;
            }

            Object firstValue = valueList.get(0);

            // Boolean property: if all values are booleans
            if (firstValue instanceof Boolean) {
                // Check if all values are booleans
                boolean allBooleans = valueList.stream().allMatch(v -> v instanceof Boolean);
                if (allBooleans) {
                    // For boolean properties, we just need the name
                    return new BooleanBlockProperty(name, false);
                }
            }

            // String/Enum property: convert all values to strings
            List<String> stringValues = new ArrayList<>();
            for (Object value : valueList) {
                if (value != null) {
                    stringValues.add(value.toString());
                }
            }

            if (!stringValues.isEmpty()) {
                String[] valuesArray = stringValues.toArray(new String[0]);
                return new EnumBlockProperty<String>(name, false, valuesArray);
            }
        }

        return null;
    }

    /**
     * Get the default value for a state.
     * For integer ranges, returns the min value.
     * For lists, returns the first value.
     *
     * @param stateValue The state value definition
     * @return The default value, or null if cannot be determined
     */
    public static Object getDefaultValue(StatesValue stateValue) {
        if (stateValue instanceof StatesValue.StatesValue_Variant1 variant1) {
            StatesValue.StatesValue_Variant1.Values values = variant1.values();
            if (values != null && values.min() != null) {
                return values.min();
            }
        } else if (stateValue instanceof StatesValue.StatesValue_Variant0 variant0) {
            List<Object> valueList = variant0.value();
            if (valueList != null && !valueList.isEmpty()) {
                return valueList.get(0);
            }
        }
        return null;
    }
}
