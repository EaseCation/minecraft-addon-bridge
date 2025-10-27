package net.easecation.bridge.core;

import java.util.List;
import java.util.Map;

public record AddonPack(
        Manifest manifest,
        List<ItemDef> items,
        List<BlockDef> blocks,
        List<EntityDef> entities,
        List<RecipeDef> recipes,
        Map<String, byte[]> resourceFiles
) {}

