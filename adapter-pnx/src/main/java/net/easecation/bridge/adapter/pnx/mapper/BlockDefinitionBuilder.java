package net.easecation.bridge.adapter.pnx.mapper;

import cn.nukkit.block.customblock.CustomBlockDefinition;
import net.easecation.bridge.core.BlockDef;

/**
 * 从BlockDef构建CustomBlockDefinition
 */
public class BlockDefinitionBuilder {

    public BlockDefinitionBuilder() {
    }

    /**
     * 从BlockDef构建CustomBlockDefinition
     */
    public CustomBlockDefinition build(BlockDef blockDef, cn.nukkit.block.customblock.CustomBlock blockInstance) {
        MappingContext context = new MappingContext(blockDef.id());
        var builder = CustomBlockDefinition.builder(blockInstance);

        // 应用texture
        if (blockDef.description() != null && blockDef.description().identifier() != null) {
            String identifier = blockDef.description().identifier();
            String textureName = identifier.contains(":") ?
                identifier.substring(identifier.indexOf(':') + 1) : identifier;
            builder.texture(textureName);
        } else {
            String textureName = blockDef.id().contains(":") ?
                blockDef.id().substring(blockDef.id().indexOf(':') + 1) : blockDef.id();
            builder.texture(textureName);
        }

        // 应用组件
        if (blockDef.components() != null) {
            var components = blockDef.components();

            // minecraft:display_name
            if (components.minecraft_displayName() != null) {
                builder.name(components.minecraft_displayName());
            }

            // minecraft:destructible_by_mining
            if (components.minecraft_destructibleByMining() != null) {
                var mining = components.minecraft_destructibleByMining();
                if (mining instanceof net.easecation.bridge.core.dto.block.v1_21_60.DestructibleByMining.DestructibleByMining_Variant0 variant0) {
                    if (!variant0.value()) {
                        // 不可破坏
                        builder.destructibleByMining(false);
                    } else {
                        builder.destructibleByMining(true);
                    }
                } else if (mining instanceof net.easecation.bridge.core.dto.block.v1_21_60.DestructibleByMining.DestructibleByMining_Variant1 variant1) {
                    if (variant1.secondsToDestroy() != null) {
                        builder.destructibleByMining(variant1.secondsToDestroy().floatValue());
                    }
                }
            }

            // minecraft:friction
            if (components.minecraft_friction() != null) {
                builder.friction(components.minecraft_friction().floatValue());
            }

            // minecraft:geometry
            if (components.minecraft_geometry() != null) {
                var geometry = components.minecraft_geometry();
                if (geometry instanceof net.easecation.bridge.core.dto.block.v1_21_60.Geometry.Geometry_Variant0 variant0) {
                    builder.geometry(variant0.value());
                } else {
                    // Variant1 可能有更复杂的结构，暂时记录 TODO
                    context.recordUnmappedComponent("minecraft:geometry", "Complex Geometry variant, needs detailed mapping");
                }
            }

            // minecraft:collision_box
            if (components.minecraft_collisionBox() != null) {
                var collisionBox = components.minecraft_collisionBox();
                if (collisionBox instanceof net.easecation.bridge.core.dto.block.v1_21_60.CollisionBox.CollisionBox_Variant0 variant0) {
                    // 如果是 false，表示没有碰撞箱
                    if (!variant0.value()) {
                        builder.collisionBox(new cn.nukkit.math.Vector3f(0, 0, 0), new cn.nukkit.math.Vector3f(0, 0, 0));
                    }
                } else if (collisionBox instanceof net.easecation.bridge.core.dto.block.v1_21_60.CollisionBox.CollisionBox_Variant1 variant1) {
                    if (variant1.origin() != null && variant1.size() != null) {
                        var origin = variant1.origin();
                        var size = variant1.size();
                        builder.collisionBox(
                            new cn.nukkit.math.Vector3f(
                                origin.get(0).floatValue(),
                                origin.get(1).floatValue(),
                                origin.get(2).floatValue()
                            ),
                            new cn.nukkit.math.Vector3f(
                                size.get(0).floatValue(),
                                size.get(1).floatValue(),
                                size.get(2).floatValue()
                            )
                        );
                    }
                }
            }

            // minecraft:selection_box
            if (components.minecraft_selectionBox() != null) {
                var selectionBox = components.minecraft_selectionBox();
                if (selectionBox instanceof net.easecation.bridge.core.dto.block.v1_21_60.SelectionBox.SelectionBox_Variant0 variant0) {
                    // 如果是 false，表示没有选择箱
                    if (!variant0.value()) {
                        builder.selectionBox(new cn.nukkit.math.Vector3f(0, 0, 0), new cn.nukkit.math.Vector3f(0, 0, 0));
                    }
                } else if (selectionBox instanceof net.easecation.bridge.core.dto.block.v1_21_60.SelectionBox.SelectionBox_Variant1 variant1) {
                    if (variant1.origin() != null && variant1.size() != null) {
                        var origin = variant1.origin();
                        var size = variant1.size();
                        builder.selectionBox(
                            new cn.nukkit.math.Vector3f(
                                origin.get(0).floatValue(),
                                origin.get(1).floatValue(),
                                origin.get(2).floatValue()
                            ),
                            new cn.nukkit.math.Vector3f(
                                size.get(0).floatValue(),
                                size.get(1).floatValue(),
                                size.get(2).floatValue()
                            )
                        );
                    }
                }
            }

            // minecraft:light_emission
            if (components.minecraft_lightEmission() != null) {
                builder.lightEmission(components.minecraft_lightEmission());
            }

            // minecraft:material_instances - 复杂组件，暂时记录 TODO
            if (components.minecraft_materialInstances() != null) {
                context.recordUnmappedComponent("minecraft:material_instances", "Complex component, requires Materials object construction");
            }
            // minecraft:loot - 需要战利品表系统
            if (components.minecraft_loot() != null) {
                context.recordUnmappedComponent("minecraft:loot", "Requires loot table system integration");
            }

            // minecraft:destructible_by_explosion
            if (components.minecraft_destructibleByExplosion() != null) {
                var explosion = components.minecraft_destructibleByExplosion();
                if (explosion instanceof net.easecation.bridge.core.dto.block.v1_21_60.DestructibleByExplosion.DestructibleByExplosion_Variant0 variant0) {
                    if (!variant0.value()) {
                        // 不可被爆炸破坏
                        builder.destructibleByExplosion(-1);
                    } else {
                        builder.destructibleByExplosion(true);
                    }
                } else if (explosion instanceof net.easecation.bridge.core.dto.block.v1_21_60.DestructibleByExplosion.DestructibleByExplosion_Variant1 variant1) {
                    if (variant1.explosionResistance() != null) {
                        builder.destructibleByExplosion(variant1.explosionResistance().intValue());
                    }
                }
            }

            // minecraft:flammable - 记录 TODO，PNX 可能没有对应的 builder 方法
            if (components.minecraft_flammable() != null) {
                context.recordUnmappedComponent("minecraft:flammable", "No corresponding builder method in CustomBlockDefinition");
            }
        }

        // 报告未映射的组件
        if (context.hasUnmappedComponents()) {
            context.info("Block has " + context.getUnmappedComponents().size() + " unmapped components");
        }

        return builder.build();
    }
}
