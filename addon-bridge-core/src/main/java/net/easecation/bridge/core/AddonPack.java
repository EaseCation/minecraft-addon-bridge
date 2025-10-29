package net.easecation.bridge.core;

import java.nio.file.Path;
import java.util.List;

/**
 * Represents a parsed addon pack with its content and metadata.
 *
 * @param manifest The manifest of the pack
 * @param items List of custom items
 * @param blocks List of custom blocks
 * @param entities List of custom entities
 * @param recipes List of custom recipes
 * @param originalPath Path to the original pack file or directory
 * @param needsPackaging Whether this pack needs to be packaged (true for directories, false for ZIP/MCPACK files)
 */
public record AddonPack(
        Manifest manifest,
        List<ItemDef> items,
        List<BlockDef> blocks,
        List<EntityDef> entities,
        List<RecipeDef> recipes,
        Path originalPath,
        boolean needsPackaging
) {}

