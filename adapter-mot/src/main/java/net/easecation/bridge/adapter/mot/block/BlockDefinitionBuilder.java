package net.easecation.bridge.adapter.mot.block;

import cn.nukkit.block.custom.CustomBlockDefinition;
import cn.nukkit.block.custom.container.BlockContainer;
import cn.nukkit.block.custom.properties.BlockProperties;
import cn.nukkit.math.Vector3f;
import net.easecation.bridge.core.BlockDef;
import net.easecation.bridge.core.dto.block.v1_21_60.*;

import java.util.List;
import java.util.Map;

/**
 * Builder for MOT CustomBlockDefinition from BlockDef.
 * Converts Bedrock behavior pack block components to MOT's CustomBlockDefinition.
 */
public class BlockDefinitionBuilder {

    /**
     * Build CustomBlockDefinition from BlockDef.
     *
     * @param sample Sample block instance for the builder
     * @param blockDef Block definition from behavior pack
     * @param properties Block properties (can be null)
     * @return CustomBlockDefinition instance
     */
    public static CustomBlockDefinition build(BlockContainer sample, BlockDef blockDef, BlockProperties properties) {
        String identifier = blockDef.id();
        Component components = blockDef.components();

        // Create builder with sample block
        CustomBlockDefinition.Builder builder = CustomBlockDefinition.builder(sample);

        if (components == null) {
            return builder.build();
        }

        // Display name
        if (components.minecraft_displayName() != null) {
            builder.name(components.minecraft_displayName());
        }

        // Geometry
        if (components.minecraft_geometry() != null) {
            String geometryId = extractGeometryIdentifier(components.minecraft_geometry());
            if (geometryId != null) {
                builder.geometry(geometryId);
            }
        }

        // Material instances (textures)
        if (components.minecraft_materialInstances() != null) {
            // MOT's materials() method requires a map of face name to texture/material
            // We'll build this from the material instances
            buildMaterials(builder, components.minecraft_materialInstances());
        }

        // Collision box
        if (components.minecraft_collisionBox() != null) {
            buildCollisionBox(builder, components.minecraft_collisionBox());
        }

        // Selection box
        if (components.minecraft_selectionBox() != null) {
            buildSelectionBox(builder, components.minecraft_selectionBox());
        }

        // Break time (from destructible_by_mining or hardness)
        // Note: MOT uses breakTime() method which sets the client-side digging time
        if (components.minecraft_destructibleByMining() != null) {
            // TODO: Extract actual break time from DestructibleByMining component
            // For now, use hardness as a fallback
            if (blockDef.hardness() > 0) {
                builder.breakTime(blockDef.hardness());
            }
        } else if (blockDef.hardness() > 0) {
            // Use hardness as break time
            builder.breakTime(blockDef.hardness());
        }

        return builder.build();
    }

    /**
     * Extract geometry identifier from Geometry component.
     */
    private static String extractGeometryIdentifier(Geometry geometry) {
        if (geometry instanceof Geometry.Geometry_Variant0 variant0) {
            return variant0.value();
        } else if (geometry instanceof Geometry.Geometry_Variant1 variant1) {
            return variant1.identifier();
        }
        return null;
    }

    /**
     * Build materials mapping from material instances.
     * TODO: Implement material instance conversion
     */
    private static void buildMaterials(CustomBlockDefinition.Builder builder,
                                       Map<String, MaterialInstancesValue> materialInstances) {
        // MOT expects a materials configuration
        // For now, we'll use a simple approach: extract texture names

        // Get the "*" (all faces) material if it exists
        MaterialInstancesValue allFaces = materialInstances.get("*");
        if (allFaces != null) {
            String texture = null;

            // Extract texture name based on variant type
            if (allFaces instanceof MaterialInstancesValue.MaterialInstancesValue_Variant0 variant0) {
                texture = variant0.value(); // String texture reference
            } else if (allFaces instanceof MaterialInstancesValue.MaterialInstancesValue_Variant1 variant1) {
                texture = variant1.texture(); // Full material definition
            }

            if (texture != null) {
                // Use simple texture() method
                builder.texture(texture);
            }
        }
    }

    /**
     * Build collision box configuration.
     */
    private static void buildCollisionBox(CustomBlockDefinition.Builder builder, CollisionBox collisionBox) {
        if (collisionBox instanceof CollisionBox.CollisionBox_Variant1 variant1) {
            List<Double> origin = variant1.origin();
            List<Double> size = variant1.size();

            if (origin != null && size != null && origin.size() >= 3 && size.size() >= 3) {
                Vector3f originVec = new Vector3f(
                    origin.get(0).floatValue(),
                    origin.get(1).floatValue(),
                    origin.get(2).floatValue()
                );
                Vector3f sizeVec = new Vector3f(
                    size.get(0).floatValue(),
                    size.get(1).floatValue(),
                    size.get(2).floatValue()
                );
                builder.collisionBox(originVec, sizeVec);
            }
        }
        // If it's a boolean variant, we don't need to do anything (default collision box)
    }

    /**
     * Build selection box configuration.
     */
    private static void buildSelectionBox(CustomBlockDefinition.Builder builder, SelectionBox selectionBox) {
        if (selectionBox instanceof SelectionBox.SelectionBox_Variant1 variant1) {
            List<Double> origin = variant1.origin();
            List<Double> size = variant1.size();

            if (origin != null && size != null && origin.size() >= 3 && size.size() >= 3) {
                Vector3f originVec = new Vector3f(
                    origin.get(0).floatValue(),
                    origin.get(1).floatValue(),
                    origin.get(2).floatValue()
                );
                Vector3f sizeVec = new Vector3f(
                    size.get(0).floatValue(),
                    size.get(1).floatValue(),
                    size.get(2).floatValue()
                );
                builder.selectionBox(originVec, sizeVec);
            }
        }
        // If it's a boolean variant, we don't need to do anything (default selection box)
    }

    /**
     * Extract break time from DestructibleByMining component.
     */
    private static Double extractBreakTime(DestructibleByMining destructible) {
        // The structure of DestructibleByMining may contain a seconds_to_destroy field
        // This is a placeholder - actual implementation depends on the DTO structure
        // For now, return null and we'll use the hardness from BlockDef
        return null;
    }
}
