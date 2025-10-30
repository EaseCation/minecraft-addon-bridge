package net.easecation.bridge.core.dto.recipe.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* Unlock achievement */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Unlock {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Unlock_Variant0(
        /* The context of the achievement to unlock */
        @JsonProperty("context") String context
    ) implements Unlock {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Unlock_Variant1(
        List<DItem> value
    ) implements Unlock {
    }
}
