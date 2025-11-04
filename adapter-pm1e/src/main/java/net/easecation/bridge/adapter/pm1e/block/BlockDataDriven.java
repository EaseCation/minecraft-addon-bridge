package net.easecation.bridge.adapter.pm1e.block;

import cn.nukkit.block.custom.container.CustomBlock;
import net.easecation.bridge.core.BlockDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data-driven custom block implementation for PM1E Nukkit.
 */
public class BlockDataDriven extends CustomBlock {

    private static final Map<String, BlockDef> BLOCK_DEF_REGISTRY = new ConcurrentHashMap<>();

    private final String identifier;

    public BlockDataDriven(String identifier, String displayName, int nukkitId) {
        super(displayName != null ? displayName : identifier, nukkitId);
        this.identifier = identifier;
    }

    public static void registerBlockDef(String identifier, BlockDef blockDef) {
        BLOCK_DEF_REGISTRY.put(identifier, blockDef);
    }

    public static BlockDef getBlockDef(String identifier) {
        return BLOCK_DEF_REGISTRY.get(identifier);
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public double getHardness() {
        BlockDef blockDef = BLOCK_DEF_REGISTRY.get(identifier);
        if (blockDef != null && blockDef.components() != null) {
            // Fix: Use Record accessor method
            var destructible = blockDef.components().minecraft_destructibleByMining();
            if (destructible != null) {
                // DestructibleByMining is a sealed interface with two variants
                if (destructible instanceof net.easecation.bridge.core.dto.block.v1_21_60.DestructibleByMining.DestructibleByMining_Variant1 variant1) {
                    if (variant1.secondsToDestroy() != null) {
                        return variant1.secondsToDestroy() * 3.0;
                    }
                } else if (destructible instanceof net.easecation.bridge.core.dto.block.v1_21_60.DestructibleByMining.DestructibleByMining_Variant0 variant0) {
                    // Variant0 is boolean: true = default hardness, false = unbreakable
                    return (variant0.value() != null && variant0.value()) ? super.getHardness() : Double.MAX_VALUE;
                }
            }
        }
        return super.getHardness();
    }

    @Override
    public double getResistance() {
        BlockDef blockDef = BLOCK_DEF_REGISTRY.get(identifier);
        if (blockDef != null && blockDef.components() != null) {
            // Fix: Use Record accessor method
            var destructible = blockDef.components().minecraft_destructibleByExplosion();
            if (destructible != null) {
                // DestructibleByExplosion is a sealed interface with two variants
                if (destructible instanceof net.easecation.bridge.core.dto.block.v1_21_60.DestructibleByExplosion.DestructibleByExplosion_Variant1 variant1) {
                    if (variant1.explosionResistance() != null) {
                        return variant1.explosionResistance();
                    }
                } else if (destructible instanceof net.easecation.bridge.core.dto.block.v1_21_60.DestructibleByExplosion.DestructibleByExplosion_Variant0 variant0) {
                    // Variant0 is boolean: true = default resistance, false = explosion-proof
                    return (variant0.value() != null && variant0.value()) ? super.getResistance() : Double.MAX_VALUE;
                }
            }
        }
        return super.getResistance();
    }

    @Override
    public double getFrictionFactor() {
        BlockDef blockDef = BLOCK_DEF_REGISTRY.get(identifier);
        if (blockDef != null && blockDef.components() != null) {
            var friction = blockDef.components().minecraft_friction();
            if (friction != null) {
                return friction;
            }
        }
        return super.getFrictionFactor();
    }

    @Override
    public int getLightLevel() {
        BlockDef blockDef = BLOCK_DEF_REGISTRY.get(identifier);
        if (blockDef != null && blockDef.components() != null) {
            var lightEmission = blockDef.components().minecraft_lightEmission();
            if (lightEmission != null) {
                return lightEmission;
            }
        }
        return super.getLightLevel();
    }
}
