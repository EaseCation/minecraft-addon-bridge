package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.template_pools;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A template pool item used to define the element and its weight. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TemplatePoolElement(
    /* A grouping of a Structure Template, the Processor List to use when placing the structure, and its weight that determines the likelihood of the element being chosen. */
    @JsonProperty("element") Element element,
    /* Specifies how structures should be placed relative to the terrain. */
    @JsonProperty("projection") @Nullable Object projection,
    /* The weighted probability of choosing the element from the pool. For example, a template pool containing 2 structures with weights of 1 and 3 will have a 25% and 75% chance of being chosen respectively. */
    @JsonProperty("weight") @Nullable Integer weight
) {
    
        /* A grouping of a Structure Template, the Processor List to use when placing the structure, and its weight that determines the likelihood of the element being chosen. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Element(
            /* The type of element. */
            @JsonProperty("element_type") String elementType,
            /* The path of the structure file. This path is relative to the behavior pack's "structures" folder. */
            @JsonProperty("location") String location,
            /* The identifier of the processor list to use when placing the structure. */
            @JsonProperty("processors") String processors
        ) {
        }
}
