package net.easecation.bridge.adapter.easecation.block;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.StringTag;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.*;

import java.util.List;
import java.util.Map;

/**
 * Utility class for converting block Components DTO to NBT format.
 * Based on ECProEntity's BlockComponents.toNBT() implementation.
 */
public class BlockComponentsNBT {

    /**
     * Convert Component to CompoundTag for block registration.
     *
     * @param component The component data from behavior pack
     * @param override Whether to override default components
     * @return CompoundTag containing all component NBT data
     */
    public static CompoundTag toNBT(Component component, boolean override) {
        if (component == null) {
            return new CompoundTag();
        }

        CompoundTag tag = new CompoundTag();

        // Light Emission - support multiple protocol versions
        if (component.minecraft_lightEmission() != null) {
            int emission = component.minecraft_lightEmission();
            // 1.12.0: 0.0~1.0 (float)
            tag.putCompound("minecraft:block_light_emission",
                new CompoundTag().putFloat("emission", Math.max(0, Math.min(1, emission / 15f))));
            // 1.19.20: 0~15 (byte)
            tag.putCompound("minecraft:light_emission",
                new CompoundTag().putByte("emission", (byte) Math.max(0, Math.min(15, emission))));
        }

        // Light Dampening - support multiple protocol versions
        if (component.minecraft_lightDampening() != null) {
            int dampening = component.minecraft_lightDampening();
            // 1.16.100: 0~15 (int)
            tag.putCompound("minecraft:block_light_absorption",
                new CompoundTag().putInt("value", dampening));
            // 1.18.10: 0~15 (byte)
            tag.putCompound("minecraft:block_light_filter",
                new CompoundTag().putByte("lightLevel", (byte) dampening));
            // 1.19.10: 0~15 (byte)
            tag.putCompound("minecraft:light_dampening",
                new CompoundTag().putByte("lightLevel", (byte) dampening));
        }

        // Collision Box
        if (component.minecraft_collisionBox() != null) {
            tag.putCompound("minecraft:collision_box", convertCollisionBox(component.minecraft_collisionBox()));
        }

        // Selection Box
        if (component.minecraft_selectionBox() != null) {
            tag.putCompound("minecraft:selection_box", convertSelectionBox(component.minecraft_selectionBox()));
        }

        // Geometry
        if (component.minecraft_geometry() != null) {
            tag.putCompound("minecraft:geometry", convertGeometry(component.minecraft_geometry()));
        } else if (!override) {
            // Default geometry for full block
            tag.putCompound("minecraft:geometry", new CompoundTag()
                .putString("identifier", "minecraft:geometry.full_block")
                .putString("culling", "")
                .putString("culling_layer", "minecraft:culling_layer.undefined")
                .putCompound("bone_visibility", new CompoundTag())
                .putBoolean("uv_lock", false)
                .putBoolean("ignoreGeometryForIsSolid", false)
                .putBoolean("useBlockTypeLightAbsorption", false)
                .putBoolean("needsLegacyTopRotation", false)
            );
        }

        // Display Name
        if (component.minecraft_displayName() != null) {
            tag.putString("minecraft:display_name",
                new CompoundTag().putString("value", component.minecraft_displayName()).toString());
        }

        // Destructible by Mining
        if (component.minecraft_destructibleByMining() != null) {
            CompoundTag destructibleTag = convertDestructibleByMining(component.minecraft_destructibleByMining());
            tag.putCompound("minecraft:destructible_by_mining", destructibleTag);
            // Also add destroy_time for compatibility
            if (destructibleTag.contains("value")) {
                tag.putCompound("minecraft:destroy_time",
                    new CompoundTag().putFloat("value", destructibleTag.getFloat("value")));
            }
        }

        // Destructible by Explosion
        if (component.minecraft_destructibleByExplosion() != null) {
            tag.putCompound("minecraft:destructible_by_explosion",
                convertDestructibleByExplosion(component.minecraft_destructibleByExplosion()));
        }

        // Flammable
        if (component.minecraft_flammable() != null) {
            tag.putCompound("minecraft:flammable", convertFlammable(component.minecraft_flammable()));
        }

        // Friction
        if (component.minecraft_friction() != null) {
            tag.putCompound("minecraft:friction",
                new CompoundTag().putFloat("value", component.minecraft_friction().floatValue()));
        }

        // Map Color
        if (component.minecraft_mapColor() != null) {
            tag.putCompound("minecraft:map_color", convertMapColor(component.minecraft_mapColor()));
        }

        // Material Instances
        if (component.minecraft_materialInstances() != null) {
            tag.putCompound("minecraft:material_instances",
                convertMaterialInstances(component.minecraft_materialInstances()));
        }

        // Placement Filter
        if (component.minecraft_placementFilter() != null) {
            tag.putCompound("minecraft:placement_filter",
                convertPlacementFilter(component.minecraft_placementFilter()));
        }

        // Transformation
        if (component.minecraft_transformation() != null) {
            tag.putCompound("minecraft:transformation",
                convertTransformation(component.minecraft_transformation()));
        }

        // Redstone Conductivity
        if (component.minecraft_redstoneConductivity() != null) {
            tag.putCompound("minecraft:redstone_conductivity",
                convertRedstoneConductivity(component.minecraft_redstoneConductivity()));
        }

        // Liquid Detection
        if (component.minecraft_liquidDetection() != null) {
            tag.putCompound("minecraft:liquid_detection",
                convertLiquidDetection(component.minecraft_liquidDetection()));
        }

        // Destruction Particles
        if (component.minecraft_destructionParticles() != null) {
            tag.putCompound("minecraft:destruction_particles",
                convertDestructionParticles(component.minecraft_destructionParticles()));
        }

        // Replaceable
        if (component.minecraft_replaceable() != null) {
            tag.putCompound("minecraft:replaceable", new CompoundTag());
        }

        // Item Visual
        if (component.minecraft_itemVisual() != null) {
            tag.putCompound("minecraft:item_visual", convertItemVisual(component.minecraft_itemVisual()));
        }

        return tag;
    }

    private static CompoundTag convertCollisionBox(CollisionBox box) {
        if (box instanceof CollisionBox.CollisionBox_Variant0 variant0) {
            // Boolean: true = default cube, false = no collision
            if (variant0.value()) {
                return new CompoundTag()
                    .putList("origin", new ListTag<>()
                        .add(new CompoundTag().putFloat("", -8))
                        .add(new CompoundTag().putFloat("", 0))
                        .add(new CompoundTag().putFloat("", -8)))
                    .putList("size", new ListTag<>()
                        .add(new CompoundTag().putFloat("", 16))
                        .add(new CompoundTag().putFloat("", 16))
                        .add(new CompoundTag().putFloat("", 16)));
            } else {
                // No collision
                return new CompoundTag()
                    .putList("origin", new ListTag<>()
                        .add(new CompoundTag().putFloat("", 0))
                        .add(new CompoundTag().putFloat("", 0))
                        .add(new CompoundTag().putFloat("", 0)))
                    .putList("size", new ListTag<>()
                        .add(new CompoundTag().putFloat("", 0))
                        .add(new CompoundTag().putFloat("", 0))
                        .add(new CompoundTag().putFloat("", 0)));
            }
        } else if (box instanceof CollisionBox.CollisionBox_Variant1 variant1) {
            CompoundTag result = new CompoundTag();

            // Origin
            if (variant1.origin() != null) {
                ListTag<CompoundTag> origin = new ListTag<>();
                for (Number num : variant1.origin()) {
                    origin.add(new CompoundTag().putFloat("", num.floatValue()));
                }
                result.putList("origin", origin);
            }

            // Size
            if (variant1.size() != null) {
                ListTag<CompoundTag> size = new ListTag<>();
                for (Number num : variant1.size()) {
                    size.add(new CompoundTag().putFloat("", num.floatValue()));
                }
                result.putList("size", size);
            }

            return result;
        }

        // Default cube
        return new CompoundTag()
            .putList("origin", new ListTag<>()
                .add(new CompoundTag().putFloat("", -8))
                .add(new CompoundTag().putFloat("", 0))
                .add(new CompoundTag().putFloat("", -8)))
            .putList("size", new ListTag<>()
                .add(new CompoundTag().putFloat("", 16))
                .add(new CompoundTag().putFloat("", 16))
                .add(new CompoundTag().putFloat("", 16)));
    }

    private static CompoundTag convertSelectionBox(SelectionBox box) {
        if (box instanceof SelectionBox.SelectionBox_Variant0 variant0) {
            // Boolean: similar to collision box
            if (variant0.value()) {
                return new CompoundTag()
                    .putList("origin", new ListTag<>()
                        .add(new CompoundTag().putFloat("", -8))
                        .add(new CompoundTag().putFloat("", 0))
                        .add(new CompoundTag().putFloat("", -8)))
                    .putList("size", new ListTag<>()
                        .add(new CompoundTag().putFloat("", 16))
                        .add(new CompoundTag().putFloat("", 16))
                        .add(new CompoundTag().putFloat("", 16)));
            } else {
                return new CompoundTag()
                    .putList("origin", new ListTag<>()
                        .add(new CompoundTag().putFloat("", 0))
                        .add(new CompoundTag().putFloat("", 0))
                        .add(new CompoundTag().putFloat("", 0)))
                    .putList("size", new ListTag<>()
                        .add(new CompoundTag().putFloat("", 0))
                        .add(new CompoundTag().putFloat("", 0))
                        .add(new CompoundTag().putFloat("", 0)));
            }
        } else if (box instanceof SelectionBox.SelectionBox_Variant1 variant1) {
            CompoundTag result = new CompoundTag();

            if (variant1.origin() != null) {
                ListTag<CompoundTag> origin = new ListTag<>();
                for (Number num : variant1.origin()) {
                    origin.add(new CompoundTag().putFloat("", num.floatValue()));
                }
                result.putList("origin", origin);
            }

            if (variant1.size() != null) {
                ListTag<CompoundTag> size = new ListTag<>();
                for (Number num : variant1.size()) {
                    size.add(new CompoundTag().putFloat("", num.floatValue()));
                }
                result.putList("size", size);
            }

            return result;
        }

        // Default
        return new CompoundTag()
            .putList("origin", new ListTag<>()
                .add(new CompoundTag().putFloat("", -8))
                .add(new CompoundTag().putFloat("", 0))
                .add(new CompoundTag().putFloat("", -8)))
            .putList("size", new ListTag<>()
                .add(new CompoundTag().putFloat("", 16))
                .add(new CompoundTag().putFloat("", 16))
                .add(new CompoundTag().putFloat("", 16)));
    }

    private static CompoundTag convertGeometry(Geometry geometry) {
        if (geometry instanceof Geometry.Geometry_Variant0 variant0) {
            // String identifier
            return new CompoundTag().putString("identifier", variant0.value());
        } else if (geometry instanceof Geometry.Geometry_Variant1 variant1) {
            CompoundTag result = new CompoundTag();
            if (variant1.identifier() != null) {
                result.putString("identifier", variant1.identifier());
            }
            if (variant1.boneVisibility() != null) {
                result.putCompound("bone_visibility", new CompoundTag()); // TODO: implement bone visibility
            }
            return result;
        }
        return new CompoundTag();
    }

    private static CompoundTag convertDestructibleByMining(DestructibleByMining destructible) {
        if (destructible instanceof DestructibleByMining.DestructibleByMining_Variant0 variant0) {
            // Boolean: true = default (0), false = unbreakable (-1)
            float hardness = variant0.value() ? 0.0f : -1.0f;
            return new CompoundTag().putFloat("value", hardness);
        } else if (destructible instanceof DestructibleByMining.DestructibleByMining_Variant1 variant1) {
            float hardness = variant1.secondsToDestroy() != null ? variant1.secondsToDestroy().floatValue() : 0.0f;
            return new CompoundTag().putFloat("value", hardness);
            // TODO: implement item_specific_speeds if needed
        }
        return new CompoundTag().putFloat("value", 0.0f);
    }

    private static CompoundTag convertDestructibleByExplosion(DestructibleByExplosion explosion) {
        if (explosion instanceof DestructibleByExplosion.DestructibleByExplosion_Variant0 variant0) {
            // Boolean: true = default, false = immune
            float resistance = variant0.value() ? 0.0f : Float.MAX_VALUE;
            return new CompoundTag().putFloat("value", resistance);
        } else if (explosion instanceof DestructibleByExplosion.DestructibleByExplosion_Variant1 variant1) {
            float resistance = variant1.explosionResistance() != null ?
                variant1.explosionResistance().floatValue() : 0.0f;
            return new CompoundTag().putFloat("value", resistance);
        }
        return new CompoundTag().putFloat("value", 0.0f);
    }

    private static CompoundTag convertFlammable(Flammable flammable) {
        if (flammable instanceof Flammable.Flammable_Variant0 variant0) {
            return new CompoundTag().putBoolean("flammable", variant0.value());
        } else if (flammable instanceof Flammable.Flammable_Variant1 variant1) {
            CompoundTag result = new CompoundTag().putBoolean("flammable", true);
            if (variant1.catchChanceModifier() != null) {
                result.putInt("catch_chance_modifier", variant1.catchChanceModifier().intValue());
            }
            if (variant1.destroyChanceModifier() != null) {
                result.putInt("destroy_chance_modifier", variant1.destroyChanceModifier().intValue());
            }
            return result;
        }
        return new CompoundTag().putBoolean("flammable", false);
    }

    private static CompoundTag convertMapColor(MapColor mapColor) {
        // MapColor is a String (hex color)
        return new CompoundTag().putString("color", mapColor.toString());
    }

    /**
     * Convert MaterialInstances to NBT.
     *
     * NOTE: The MaterialInstances DTO is currently an empty record because it uses
     * JSON Schema's additionalProperties for dynamic face/instance names.
     *
     * For now, we create an empty materials structure. Full implementation requires:
     * 1. Either changing MaterialInstances to extend Map<String, Instance>
     * 2. Or using Jackson's @JsonAnySetter to capture dynamic properties
     * 3. Or passing the raw JSON Object through for conversion
     *
     * Reference structure from ECProEntity:
     * {
     *   "materials": {
     *     "up": { "ambient_occlusion": 1.0, "face_dimming": true, ... },
     *     "down": { ... },
     *     "*": { ... }
     *   },
     *   "mappings": {}
     * }
     */
    private static CompoundTag convertMaterialInstances(MaterialInstances instances) {
        CompoundTag result = new CompoundTag();

        // Create empty materials structure
        CompoundTag materials = new CompoundTag();
        // TODO: Parse dynamic material instances from JSON
        // This requires MaterialInstances DTO to support additionalProperties
        // or be changed to Map<String, MaterialInstance>

        result.putCompound("materials", materials);
        result.putCompound("mappings", new CompoundTag());

        return result;
    }

    /**
     * Convert PlacementFilter to NBT.
     *
     * PlacementFilter determines where a block can be placed. It contains conditions
     * that specify allowed faces and blocks that can be placed on.
     *
     * Structure:
     * {
     *   "conditions": [
     *     {
     *       "allowed_faces": 0x3F (bitfield: down|up|north|south|west|east),
     *       "block_filter": [
     *         { "name": "minecraft:stone", "states": [...] },
     *         { "tags": "minecraft:dirt", "tags_version": 0 }
     *       ]
     *     }
     *   ]
     * }
     */
    @SuppressWarnings("unchecked")
    private static CompoundTag convertPlacementFilter(PlacementFilter filter) {
        CompoundTag result = new CompoundTag();
        ListTag<CompoundTag> conditionTags = new ListTag<>();

        if (filter.conditions() != null) {
            int conditionIndex = 0;
            for (Object conditionObj : filter.conditions()) {
                try {
                    CompoundTag conditionTag = new CompoundTag();

                    // Parse condition as Map
                    if (!(conditionObj instanceof Map)) {
                        System.err.println("[BlockComponentsNBT] Warning: Placement filter condition #" + conditionIndex +
                            " is not a Map (got " + conditionObj.getClass().getSimpleName() + "), skipping");
                        continue;
                    }
                    Map<String, Object> conditionMap = (Map<String, Object>) conditionObj;

                // Parse allowed_faces
                Object allowedFacesObj = conditionMap.get("allowed_faces");
                int allowedFaces = 0x3F; // Default: all faces
                if (allowedFacesObj instanceof List) {
                    for (Object face : (List<?>) allowedFacesObj) {
                        String faceStr = face.toString().toLowerCase();
                        allowedFaces = applyAllowedFace(allowedFaces, faceStr);
                    }
                }
                conditionTag.putByte("allowed_faces", (byte) allowedFaces);

                // Parse block_filter
                ListTag<CompoundTag> blockFilterTags = new ListTag<>();
                Object blockFilterObj = conditionMap.get("block_filter");
                if (blockFilterObj instanceof List) {
                    for (Object blockObj : (List<?>) blockFilterObj) {
                        CompoundTag blockTag = convertBlockFilter(blockObj);
                        if (blockTag != null) {
                            blockFilterTags.add(blockTag);
                        }
                    }
                }
                    conditionTag.putList("block_filter", blockFilterTags);

                    conditionTags.add(conditionTag);

                } catch (Exception e) {
                    System.err.println("[BlockComponentsNBT] Error processing placement filter condition #" + conditionIndex + ": " + e.getMessage());
                    // Continue with next condition
                } finally {
                    conditionIndex++;
                }
            }
        }

        result.putList("conditions", conditionTags);
        return result;
    }

    /**
     * Apply allowed face to bitfield.
     * Bitfield encoding: DOWN=0x1, UP=0x2, NORTH=0x4, SOUTH=0x8, WEST=0x10, EAST=0x20
     */
    private static int applyAllowedFace(int current, String face) {
        return switch (face) {
            case "down" -> current | 0x1;
            case "up" -> current | 0x2;
            case "north" -> current | 0x4;
            case "south" -> current | 0x8;
            case "west" -> current | 0x10;
            case "east" -> current | 0x20;
            case "side" -> current | 0x3C; // north|south|west|east
            case "all" -> current | 0x3F; // all 6 faces
            default -> current;
        };
    }

    /**
     * Convert a block filter entry to NBT.
     * Can be either a string (block name) or an object (BlockDescriptor).
     */
    @SuppressWarnings("unchecked")
    private static CompoundTag convertBlockFilter(Object blockObj) {
        CompoundTag blockTag = new CompoundTag();

        if (blockObj instanceof String) {
            // Simple block name
            blockTag.putString("name", (String) blockObj);
        } else if (blockObj instanceof Map) {
            Map<String, Object> blockMap = (Map<String, Object>) blockObj;

            // Check if it's a tag-based filter
            Object tags = blockMap.get("tags");
            if (tags != null) {
                blockTag.putString("tags", tags.toString());
                blockTag.putInt("tags_version", 0);
            } else {
                // Name-based filter with optional states
                Object name = blockMap.get("name");
                if (name != null) {
                    blockTag.putString("name", name.toString());

                    // Parse block states
                    Object statesObj = blockMap.get("states");
                    if (statesObj instanceof Map) {
                        Map<String, Object> statesMap = (Map<String, Object>) statesObj;
                        ListTag<CompoundTag> stateTags = new ListTag<>();

                        for (Map.Entry<String, Object> entry : statesMap.entrySet()) {
                            CompoundTag stateTag = new CompoundTag();
                            stateTag.putString("state", entry.getKey());

                            Object value = entry.getValue();
                            if (value instanceof String) {
                                stateTag.putString("value", (String) value);
                            } else if (value instanceof Boolean) {
                                stateTag.putBoolean("value", (Boolean) value);
                            } else if (value instanceof Number) {
                                stateTag.putInt("value", ((Number) value).intValue());
                            }

                            stateTags.add(stateTag);
                        }

                        blockTag.putList("states", stateTags);
                    }
                }
            }
        }

        return blockTag.getTags().isEmpty() ? null : blockTag;
    }

    private static CompoundTag convertTransformation(Transformation transformation) {
        CompoundTag result = new CompoundTag();
        if (transformation.rotation() != null) {
            ListTag<CompoundTag> rotation = new ListTag<>();
            for (Number num : transformation.rotation()) {
                rotation.add(new CompoundTag().putFloat("", num.floatValue()));
            }
            result.putList("rotation", rotation);
        }
        if (transformation.scale() != null) {
            ListTag<CompoundTag> scale = new ListTag<>();
            for (Number num : transformation.scale()) {
                scale.add(new CompoundTag().putFloat("", num.floatValue()));
            }
            result.putList("scale", scale);
        }
        if (transformation.translation() != null) {
            ListTag<CompoundTag> translation = new ListTag<>();
            for (Number num : transformation.translation()) {
                translation.add(new CompoundTag().putFloat("", num.floatValue()));
            }
            result.putList("translation", translation);
        }
        return result;
    }

    private static CompoundTag convertRedstoneConductivity(RedstoneConductivity conductivity) {
        CompoundTag result = new CompoundTag();
        if (conductivity.redstoneConductor() != null) {
            result.putBoolean("redstoneConductor", conductivity.redstoneConductor());
        }
        if (conductivity.allowsWireToStepDown() != null) {
            result.putBoolean("allowsWireToStepDown", conductivity.allowsWireToStepDown());
        }
        return result;
    }

    /**
     * Convert LiquidDetection to NBT.
     *
     * LiquidDetection specifies how a block behaves when detecting liquid (e.g., waterlogging).
     *
     * Structure:
     * {
     *   "detectionRules": [
     *     {
     *       "liquid_type": "water",
     *       "can_contain_liquid": true,
     *       "on_liquid_touches": "blocking", // blocking|broken|popped|no_reaction
     *       "stops_liquid_flowing_from_direction": ["down", "up"]
     *     }
     *   ]
     * }
     */
    private static CompoundTag convertLiquidDetection(LiquidDetection detection) {
        CompoundTag result = new CompoundTag();
        ListTag<CompoundTag> rules = new ListTag<>();

        if (detection.detectionRules() != null) {
            for (DefinitionRule rule : detection.detectionRules()) {
                CompoundTag ruleTag = new CompoundTag();

                // Liquid type
                if (rule.liquidType() != null) {
                    ruleTag.putString("liquid_type", rule.liquidType());
                }

                // Can contain liquid (waterlogging)
                if (rule.canContainLiquid() != null) {
                    ruleTag.putBoolean("can_contain_liquid", rule.canContainLiquid());
                }

                // On liquid touches reaction
                if (rule.onLiquidTouches() != null) {
                    ruleTag.putString("on_liquid_touches", rule.onLiquidTouches());
                }

                // Stops liquid flowing from direction
                if (rule.stopsLiquidFlowingFromDirection() != null && !rule.stopsLiquidFlowingFromDirection().isEmpty()) {
                    ListTag<StringTag> directions = new ListTag<>();
                    for (String direction : rule.stopsLiquidFlowingFromDirection()) {
                        directions.add(new StringTag("", direction));
                    }
                    ruleTag.putList("stops_liquid_flowing_from_direction", directions);
                }

                rules.add(ruleTag);
            }
        }

        result.putList("detectionRules", rules);
        return result;
    }

    private static CompoundTag convertDestructionParticles(DestructionParticles particles) {
        CompoundTag result = new CompoundTag();
        if (particles.texture() != null) {
            result.putString("texture", particles.texture());
        }
        if (particles.tintMethod() != null) {
            result.putString("tint_method", particles.tintMethod().toString());
        }
        // Note: particle_count is not in the v1_21_60 schema, using default
        result.putInt("particle_count", 100);
        return result;
    }

    /**
     * Convert ItemVisual to NBT.
     *
     * ItemVisual specifies the geometry and material used when the block is in item form.
     *
     * Structure:
     * {
     *   "geometryDescription": { ... geometry NBT ... },
     *   "materialInstancesDescription": { ... material instances NBT ... }
     * }
     */
    private static CompoundTag convertItemVisual(ItemVisual visual) {
        CompoundTag result = new CompoundTag();

        // Convert geometry component
        if (visual.geometry() != null) {
            CompoundTag geometryNBT = convertGeometry(visual.geometry());
            result.putCompound("geometryDescription", geometryNBT);
        }

        // Convert material instances component
        if (visual.materialInstances() != null) {
            CompoundTag materialNBT = convertMaterialInstances(visual.materialInstances());
            result.putCompound("materialInstancesDescription", materialNBT);
        }

        return result;
    }
}
