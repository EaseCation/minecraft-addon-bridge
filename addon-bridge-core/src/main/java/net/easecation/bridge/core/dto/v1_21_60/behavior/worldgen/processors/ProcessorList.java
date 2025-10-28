package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.processors;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProcessorList(
    /* The description of this jigsaw. */
    @JsonProperty("description") Description description,
    /* A list of processors. */
    @JsonProperty("processors") List<net.easecation.bridge.core.dto.EmptyObject> processors
) {
    
        /* The description of this jigsaw. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* Identifier of the Processor List. This is referenced by Template Pools when pairing processors with Structure Templates. */
            @JsonProperty("identifier") @Nullable String identifier
        ) {
        }
}
