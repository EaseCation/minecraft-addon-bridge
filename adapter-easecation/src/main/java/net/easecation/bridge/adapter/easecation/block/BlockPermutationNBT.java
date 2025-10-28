package net.easecation.bridge.adapter.easecation.block;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Component;

import java.util.List;
import java.util.Map;

/**
 * Utility class for converting block permutations (List<Object>) to NBT format.
 * Based on ECProEntity's BlockPermutation.toNBT() implementation.
 *
 * Permutations allow blocks to dynamically change their components based on conditions.
 * Conditions are expressed as Molang expressions that evaluate to true/false.
 *
 * Example JSON format:
 * {
 *   "permutations": [
 *     {
 *       "condition": "q.block_state('my:powered') == true",
 *       "components": {
 *         "minecraft:light_emission": 0,
 *         "minecraft:geometry": "geometry.powered"
 *       }
 *     }
 *   ]
 * }
 */
public class BlockPermutationNBT {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Convert permutations list to NBT ListTag for block registration.
     *
     * @param permutations List of permutation objects (each containing condition and components)
     * @return ListTag containing all permutation NBT data
     */
    public static ListTag<CompoundTag> toNBT(List<Object> permutations) {
        ListTag<CompoundTag> listTag = new ListTag<>();

        if (permutations == null || permutations.isEmpty()) {
            return listTag;
        }

        for (Object permObj : permutations) {
            try {
                CompoundTag permutationTag = convertPermutation(permObj);
                if (permutationTag != null) {
                    listTag.add(permutationTag);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(
                    "Failed to convert permutation: " + e.getMessage(), e);
            }
        }

        return listTag;
    }

    /**
     * Convert a single permutation object to NBT.
     *
     * Expected structure:
     * {
     *   "condition": "molang_expression",
     *   "components": { ... }
     * }
     *
     * @param permObj Permutation object (typically a Map)
     * @return CompoundTag for the permutation
     */
    @SuppressWarnings("unchecked")
    private static CompoundTag convertPermutation(Object permObj) {
        if (!(permObj instanceof Map)) {
            throw new IllegalArgumentException(
                "Permutation must be a Map, got: " + permObj.getClass());
        }

        Map<String, Object> permMap = (Map<String, Object>) permObj;

        // Extract condition (Molang expression)
        String condition = extractCondition(permMap);
        if (condition == null || condition.isEmpty()) {
            throw new IllegalArgumentException(
                "Permutation missing 'condition' field");
        }

        // Extract components
        Object componentsObj = permMap.get("components");
        if (componentsObj == null) {
            throw new IllegalArgumentException(
                "Permutation missing 'components' field");
        }

        // Convert components map to Component DTO
        Component component = convertToComponent(componentsObj);

        // Build NBT
        CompoundTag tag = new CompoundTag();
        tag.putString("condition", condition);

        // Convert components to NBT (with override=true to prevent default values)
        CompoundTag componentsNBT = BlockComponentsNBT.toNBT(component, true);
        tag.putCompound("components", componentsNBT);

        return tag;
    }

    /**
     * Extract condition string from permutation map.
     * Handles both "condition" and "conditions" (legacy) field names.
     *
     * @param permMap Permutation map
     * @return Condition Molang expression string
     */
    private static String extractCondition(Map<String, Object> permMap) {
        // Try "condition" field first
        Object condition = permMap.get("condition");
        if (condition != null) {
            return condition.toString();
        }

        // Try "conditions" field (legacy)
        condition = permMap.get("conditions");
        if (condition != null) {
            return condition.toString();
        }

        return null;
    }

    /**
     * Convert components object to Component DTO.
     *
     * @param componentsObj Components object (Map or already a Component)
     * @return Component DTO
     */
    private static Component convertToComponent(Object componentsObj) {
        try {
            // If already a Component, return it
            if (componentsObj instanceof Component) {
                return (Component) componentsObj;
            }

            // If it's a Map, convert it to Component using Jackson
            if (componentsObj instanceof Map) {
                // Jackson can convert Map to record class
                return MAPPER.convertValue(componentsObj, Component.class);
            }

            throw new IllegalArgumentException(
                "Components must be a Map or Component, got: " + componentsObj.getClass());

        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Failed to convert components to Component DTO: " + e.getMessage(), e);
        }
    }

    // ============================================================================
    // Molang Expression Utilities (for future condition evaluation)
    // ============================================================================

    /**
     * Validate a Molang condition expression.
     * This is a basic validation - full Molang parsing would be complex.
     *
     * @param condition The Molang expression to validate
     * @return true if the condition appears valid
     */
    public static boolean isValidMolangCondition(String condition) {
        if (condition == null || condition.trim().isEmpty()) {
            return false;
        }

        // Basic validation: check for common Molang keywords
        String lower = condition.toLowerCase();
        return lower.contains("q.") ||           // query
               lower.contains("v.") ||           // variable
               lower.contains("t.") ||           // temp
               lower.contains("math.") ||        // math functions
               lower.contains("==") ||           // comparison
               lower.contains("!=") ||
               lower.contains(">") ||
               lower.contains("<") ||
               lower.contains("&&") ||           // logical
               lower.contains("||") ||
               lower.matches("\\d+\\.?\\d*");    // numeric literal
    }

    /**
     * Extract referenced block states from a Molang condition.
     * Useful for debugging and validation.
     *
     * Example: "q.block_state('my:powered') == true" -> ["my:powered"]
     *
     * @param condition The Molang expression
     * @return List of block state names referenced in the condition
     */
    public static List<String> extractBlockStates(String condition) {
        // Simple regex to match q.block_state('state_name')
        java.util.regex.Pattern pattern =
            java.util.regex.Pattern.compile("q\\.block_state\\(['\"]([^'\"]+)['\"]\\)");
        java.util.regex.Matcher matcher = pattern.matcher(condition);

        java.util.List<String> states = new java.util.ArrayList<>();
        while (matcher.find()) {
            states.add(matcher.group(1));
        }

        return states;
    }

    // TODO: Full Molang expression evaluation
    // This would require implementing a Molang parser and evaluator
    // Reference: https://bedrock.dev/docs/stable/Molang
    //
    // Example implementation would need to:
    // 1. Parse Molang expressions into AST
    // 2. Provide query context (q.block_state, etc.)
    // 3. Evaluate expressions to boolean
    //
    // For now, permutations work on the client side, so server-side
    // evaluation is not strictly necessary.
}
