package net.easecation.bridge.core.versioned.upgrade;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.versioned.FormatVersion;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Manual upgrade step for Entity v1.20.41 -> v1.20.81.
 * Handles the conversion of specific component types to Attribute.
 *
 * In v1.20.81, the following components changed from specific types to Attribute:
 * - minecraft:health (Health → Attribute)
 * - minecraft:attack_damage (AttackDamage → Attribute)
 * - minecraft:follow_range (FollowRange → Attribute)
 * - minecraft:knockback_resistance (KnockbackResistance → Attribute)
 * - minecraft:lava_movement (LavaMovement → Attribute)
 * - minecraft:movement (Movement → Attribute)
 * - minecraft:underwater_movement (UnderwaterMovement → Attribute)
 */
public class EntityComponentsUpgradeStep_v1_20_41_to_v1_20_81
    implements UpgradeStep<net.easecation.bridge.core.dto.entity.v1_20_41.Entity,
                           net.easecation.bridge.core.dto.entity.v1_20_81.Entity> {

    private final FormatVersion fromVersion = FormatVersion.V1_20_41;
    private final FormatVersion toVersion = FormatVersion.V1_20_81;
    private final ObjectMapper mapper;
    private final GenericUpgradeStep<net.easecation.bridge.core.dto.entity.v1_20_41.Entity,
                                     net.easecation.bridge.core.dto.entity.v1_20_81.Entity> genericStep;

    public EntityComponentsUpgradeStep_v1_20_41_to_v1_20_81(ObjectMapper mapper) {
        this.mapper = mapper;
        this.genericStep = new GenericUpgradeStep<>(
            fromVersion,
            toVersion,
            net.easecation.bridge.core.dto.entity.v1_20_41.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_20_81.Entity.class,
            mapper
        );
    }

    @Override
    public FormatVersion fromVersion() {
        return fromVersion;
    }

    @Override
    public FormatVersion toVersion() {
        return toVersion;
    }

    @Override
    public net.easecation.bridge.core.dto.entity.v1_20_81.Entity upgrade(
        net.easecation.bridge.core.dto.entity.v1_20_41.Entity oldEntity,
        UpgradeContext context
    ) throws UpgradeException {
        BridgeLoggerHolder.getLogger().debug(String.format(
            "[%s->%s] Using manual upgrade for Entity (Components type changes)",
            fromVersion, toVersion));

        try {
            // Step 1: Use JSON serialization to convert the entire Entity
            // This handles all fields except the 7 special component types
            String json = mapper.writeValueAsString(oldEntity);

            // Parse as Map to manipulate the JSON
            @SuppressWarnings("unchecked")
            Map<String, Object> entityMap = mapper.readValue(json, Map.class);

            // Step 2: Extract and convert the components field
            @SuppressWarnings("unchecked")
            Map<String, Object> componentsMap = (Map<String, Object>) entityMap.get("components");

            if (componentsMap != null) {
                // Convert each special component type
                convertHealthComponent(componentsMap, context);
                convertAttackDamageComponent(componentsMap, context);
                convertFollowRangeComponent(componentsMap, context);
                convertKnockbackResistanceComponent(componentsMap, context);
                convertLavaMovementComponent(componentsMap, context);
                convertMovementComponent(componentsMap, context);
                convertUnderwaterMovementComponent(componentsMap, context);
            }

            // Step 3: Convert back to Entity
            String modifiedJson = mapper.writeValueAsString(entityMap);
            return mapper.readValue(modifiedJson, net.easecation.bridge.core.dto.entity.v1_20_81.Entity.class);

        } catch (Exception e) {
            throw new UpgradeException(fromVersion, toVersion,
                "Failed to upgrade Entity with component type conversions: " + e.getMessage(), e);
        }
    }

    /**
     * Convert minecraft:health from Health type to Attribute type.
     * Old: { "max": Integer, "value": Object }
     * New: { "min": Double, "max": Double, "value": Range_a_B }
     */
    private void convertHealthComponent(Map<String, Object> components, UpgradeContext context) {
        Object health = components.get("minecraft:health");
        if (health == null) return;

        @SuppressWarnings("unchecked")
        Map<String, Object> healthMap = (Map<String, Object>) health;

        Map<String, Object> attributeMap = convertToAttribute(
            healthMap.get("value"),
            healthMap.get("max"),
            null  // min doesn't exist in old version
        );

        components.put("minecraft:health", attributeMap);
        BridgeLoggerHolder.getLogger().debug("[v1.20.41->v1.20.81] Converted minecraft:health to Attribute");
    }

    /**
     * Convert minecraft:attack_damage from AttackDamage type to Attribute type.
     * Old: { "value": Integer }
     * New: { "min": Double, "max": Double, "value": Range_a_B }
     */
    private void convertAttackDamageComponent(Map<String, Object> components, UpgradeContext context) {
        Object attackDamage = components.get("minecraft:attack_damage");
        if (attackDamage == null) return;

        @SuppressWarnings("unchecked")
        Map<String, Object> attackDamageMap = (Map<String, Object>) attackDamage;

        Map<String, Object> attributeMap = convertToAttribute(
            attackDamageMap.get("value"),
            null,  // max doesn't exist in old version
            null   // min doesn't exist in old version
        );

        components.put("minecraft:attack_damage", attributeMap);
        BridgeLoggerHolder.getLogger().debug("[v1.20.41->v1.20.81] Converted minecraft:attack_damage to Attribute");
    }

    /**
     * Convert minecraft:follow_range from FollowRange type to Attribute type.
     * Old: { "value": Integer, "max": Integer }
     * New: { "min": Double, "max": Double, "value": Range_a_B }
     */
    private void convertFollowRangeComponent(Map<String, Object> components, UpgradeContext context) {
        Object followRange = components.get("minecraft:follow_range");
        if (followRange == null) return;

        @SuppressWarnings("unchecked")
        Map<String, Object> followRangeMap = (Map<String, Object>) followRange;

        Map<String, Object> attributeMap = convertToAttribute(
            followRangeMap.get("value"),
            followRangeMap.get("max"),
            null  // min doesn't exist in old version
        );

        components.put("minecraft:follow_range", attributeMap);
        BridgeLoggerHolder.getLogger().debug("[v1.20.41->v1.20.81] Converted minecraft:follow_range to Attribute");
    }

    /**
     * Convert minecraft:knockback_resistance from KnockbackResistance type to Attribute type.
     * Old: { "value": Double, "maximum": Double }
     * New: { "min": Double, "max": Double, "value": Range_a_B }
     */
    private void convertKnockbackResistanceComponent(Map<String, Object> components, UpgradeContext context) {
        Object knockbackResistance = components.get("minecraft:knockback_resistance");
        if (knockbackResistance == null) return;

        @SuppressWarnings("unchecked")
        Map<String, Object> knockbackResistanceMap = (Map<String, Object>) knockbackResistance;

        Map<String, Object> attributeMap = convertToAttribute(
            knockbackResistanceMap.get("value"),
            knockbackResistanceMap.get("maximum"),  // "maximum" in old version maps to "max" in new version
            null  // min doesn't exist in old version
        );

        components.put("minecraft:knockback_resistance", attributeMap);
        BridgeLoggerHolder.getLogger().debug("[v1.20.41->v1.20.81] Converted minecraft:knockback_resistance to Attribute");
    }

    /**
     * Convert minecraft:lava_movement from LavaMovement type to Attribute type.
     * Old: { "value": Double }
     * New: { "min": Double, "max": Double, "value": Range_a_B }
     */
    private void convertLavaMovementComponent(Map<String, Object> components, UpgradeContext context) {
        Object lavaMovement = components.get("minecraft:lava_movement");
        if (lavaMovement == null) return;

        @SuppressWarnings("unchecked")
        Map<String, Object> lavaMovementMap = (Map<String, Object>) lavaMovement;

        Map<String, Object> attributeMap = convertToAttribute(
            lavaMovementMap.get("value"),
            null,  // max doesn't exist in old version
            null   // min doesn't exist in old version
        );

        components.put("minecraft:lava_movement", attributeMap);
        BridgeLoggerHolder.getLogger().debug("[v1.20.41->v1.20.81] Converted minecraft:lava_movement to Attribute");
    }

    /**
     * Convert minecraft:movement from Movement type to Attribute type.
     * Old: { "value": Object, "max": Double }
     * New: { "min": Double, "max": Double, "value": Range_a_B }
     */
    private void convertMovementComponent(Map<String, Object> components, UpgradeContext context) {
        Object movement = components.get("minecraft:movement");
        if (movement == null) return;

        @SuppressWarnings("unchecked")
        Map<String, Object> movementMap = (Map<String, Object>) movement;

        Map<String, Object> attributeMap = convertToAttribute(
            movementMap.get("value"),
            movementMap.get("max"),
            null  // min doesn't exist in old version
        );

        components.put("minecraft:movement", attributeMap);
        BridgeLoggerHolder.getLogger().debug("[v1.20.41->v1.20.81] Converted minecraft:movement to Attribute");
    }

    /**
     * Convert minecraft:underwater_movement from UnderwaterMovement type to Attribute type.
     * Old: { "value": Double }
     * New: { "min": Double, "max": Double, "value": Range_a_B }
     */
    private void convertUnderwaterMovementComponent(Map<String, Object> components, UpgradeContext context) {
        Object underwaterMovement = components.get("minecraft:underwater_movement");
        if (underwaterMovement == null) return;

        @SuppressWarnings("unchecked")
        Map<String, Object> underwaterMovementMap = (Map<String, Object>) underwaterMovement;

        Map<String, Object> attributeMap = convertToAttribute(
            underwaterMovementMap.get("value"),
            null,  // max doesn't exist in old version
            null   // min doesn't exist in old version
        );

        components.put("minecraft:underwater_movement", attributeMap);
        BridgeLoggerHolder.getLogger().debug("[v1.20.41->v1.20.81] Converted minecraft:underwater_movement to Attribute");
    }

    /**
     * Convert old component fields to Attribute structure.
     *
     * @param oldValue The old "value" field (can be Integer, Double, Object, etc.)
     * @param oldMax The old "max" or "maximum" field (if exists)
     * @param oldMin The old "min" field (if exists)
     * @return A Map representing the new Attribute structure
     */
    private Map<String, Object> convertToAttribute(Object oldValue, Object oldMax, Object oldMin) {
        Map<String, Object> attribute = new java.util.HashMap<>();

        // Convert min
        if (oldMin != null) {
            attribute.put("min", convertToDouble(oldMin));
        }

        // Convert max
        if (oldMax != null) {
            attribute.put("max", convertToDouble(oldMax));
        }

        // Convert value to Range_a_B format
        if (oldValue != null) {
            Object rangeValue = convertToRangeAB(oldValue);
            if (rangeValue != null) {
                attribute.put("value", rangeValue);
            }
        }

        return attribute;
    }

    /**
     * Convert a value to Range_a_B format.
     * Range_a_B is a sealed interface with three variants:
     * - Variant0: Single Double value
     * - Variant1: List of Doubles
     * - Variant2: Object with range_min and range_max
     *
     * @param value The value to convert
     * @return A Map/List/Double representing the Range_a_B value
     */
    private Object convertToRangeAB(Object value) {
        if (value == null) {
            return null;
        }

        // If it's already a Number, convert to Double (Variant0)
        if (value instanceof Number) {
            return convertToDouble(value);
        }

        // If it's a List, convert all elements to Double (Variant1)
        if (value instanceof List) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) value;
            return list.stream()
                .map(this::convertToDouble)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }

        // If it's a Map with range_min/range_max, keep as is (Variant2)
        if (value instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) value;

            // Check if it's a range object
            if (map.containsKey("range_min") || map.containsKey("range_max")) {
                Map<String, Object> rangeMap = new java.util.HashMap<>();

                Object rangeMin = map.get("range_min");
                if (rangeMin != null) {
                    rangeMap.put("range_min", convertToDouble(rangeMin));
                }

                Object rangeMax = map.get("range_max");
                if (rangeMax != null) {
                    rangeMap.put("range_max", convertToDouble(rangeMax));
                }

                return rangeMap;
            }
        }

        // Try to parse as Double
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            BridgeLoggerHolder.getLogger().warning(
                "Failed to convert value to Range_a_B: " + value + " (type: " + value.getClass().getSimpleName() + ")");
            return null;
        }
    }

    /**
     * Convert a value to Double.
     *
     * @param value The value to convert (can be Integer, Double, String, etc.)
     * @return The Double value, or null if conversion fails
     */
    private Double convertToDouble(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }

        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
