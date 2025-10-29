package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface JigsawStructuresC {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record JigsawStructuresC_Variant0(
        List<Object> value
    ) implements JigsawStructuresC {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION) 
    record JigsawStructuresC_Variant1(
    ) implements JigsawStructuresC {
    }
}
