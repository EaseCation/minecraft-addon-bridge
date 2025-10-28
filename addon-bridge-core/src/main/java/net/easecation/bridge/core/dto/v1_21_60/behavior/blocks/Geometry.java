package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;
import javax.annotation.Nullable;

/* The description identifier of the geometry file to use to render this block. This identifier must match an existing geometry identifier in any of the currently loaded resource packs. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Geometry {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Geometry_Variant0(
        String value
    ) implements Geometry {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Geometry_Variant1(
        /* The description identifier of the geometry file to use to render this block. This identifier must match an existing geometry identifier in any of the currently loaded resource packs. */
        @JsonProperty("identifier") String identifier,
        /* A list of bones that should be visible when rendering this block. If not specified, all bones will be visible. */
        @JsonProperty("bone_visibility") @Nullable Map<String, Object> boneVisibility,
        /* The description identifer of the block culling rule used to cull this block. This identifier must match an existing geometry identifier in any of the currently loaded resource packs. */
        @JsonProperty("culling") @Nullable String culling,
        /* A string that allows culling rule to group multiple blocks together when comparing them. */
        @JsonProperty("culling_layer") @Nullable String cullingLayer
    ) implements Geometry {}
}
