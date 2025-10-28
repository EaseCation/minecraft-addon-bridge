package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.template_pools;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TemplatePool(
    /* The description of this template pool. */
    @JsonProperty("description") Description description,
    /* An array of pool elements. */
    @JsonProperty("elements") List<Object> elements,
    /* Fallback template pool to use if no element in the pool can be placed successfully. */
    @JsonProperty("fallback") @Nullable String fallback
) {
    
        /* The description of this template pool. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* Identifier of the template pool. This is used by both the start_pool property of the Jigsaw Structure JSON and the Jigsaw Block's Target Pool field. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
