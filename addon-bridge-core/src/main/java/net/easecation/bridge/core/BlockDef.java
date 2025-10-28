package net.easecation.bridge.core;

import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.BlockDefinitions;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Represents a custom block definition parsed from behavior pack.
 * Contains full block description, components, and permutations from DTO.
 */
public record BlockDef(
        String id,
        @Nullable BlockDefinitions.Description description,
        @Nullable Component components,
        @Nullable List<Object> permutations,
        // Legacy fields for backward compatibility
        @Nullable Map<String, Object> states,
        double hardness
) {
    // Legacy constructor for backward compatibility
    public BlockDef(String id, Map<String, Object> states, double hardness) {
        this(id, null, null, null, states, hardness);
    }

    // Constructor from DTO
    public static BlockDef fromDTO(BlockDefinitions dto) {
        String identifier = dto.description() != null && dto.description().identifier() != null
                ? dto.description().identifier().toString()
                : "unknown:block";

        return new BlockDef(
                identifier,
                dto.description(),
                dto.components(),
                dto.permutations(),
                null,
                0.0
        );
    }
}

