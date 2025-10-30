package net.easecation.bridge.core.dto.block.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* A custom block definition. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlockDefinitions(
    /* The description for this block. */
    @JsonProperty("description") Description description,
    @JsonProperty("events") @Nullable Events events,
    @JsonProperty("components") Component components,
    /* UNDOCUMENTED. */
    @JsonProperty("permutations") @Nullable List<Object> permutations
) {
    
        /* The description for this block. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The identifier for this block. The name must include a namespace and must not use the Minecraft namespace unless overriding a Vanilla block. */
            @JsonProperty("identifier") String identifier,
            /* If this block is experimental, it will only be registered if the world is marked as experimantal. */
            @JsonProperty("is_experimental") @Nullable Boolean isExperimental,
            /* Whether or not to register this block to the creative inventory menu. */
            @JsonProperty("register_to_creative_menu") @Nullable Boolean registerToCreativeMenu,
            /* UNDOCUMENTED. */
            @JsonProperty("properties") @Nullable Map<String, Object> properties
        ) {
        }
}
