package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Entity(
    /* The description of the this entity. */
    @JsonProperty("description") Description description,
    /* Each group when add / remove the default components. */
    @JsonProperty("component_groups") @Nullable Map<String, Components> componentGroups,
    /* The components that are added as the foundation of the entity. */
    @JsonProperty("components") @Nullable Components components,
    /* The events that the entity can run, these add or remove components_groups. */
    @JsonProperty("events") @Nullable Events events
) {
    
        /* The description of the this entity. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* Sets the mapping of internal animation / animation controllers references to actual animations. This is a JSON Object of name/animation pairs */
            @JsonProperty("animations") @Nullable Map<String, String> animations,
            /* Sets the identifier for this entity's description. */
            @JsonProperty("identifier") String identifier,
            /* Sets whether or not this entity has a spawn egg in the creative ui. */
            @JsonProperty("is_spawnable") @Nullable Boolean isSpawnable,
            /* Sets whether or not we can summon this entity using commands such as /summon. */
            @JsonProperty("is_summonable") @Nullable Boolean isSummonable,
            /* Sets whether or not this entity is experimental. Experimental entities are only enabled when the experimental toggle is enabled. */
            @JsonProperty("is_experimental") @Nullable Boolean isExperimental,
            /* Experimental */
            @JsonProperty("properties") @Nullable Map<String, Object> properties,
            /* Sets the name for the Vanilla Minecraft identifier this entity will use to build itself from. */
            @JsonProperty("runtime_identifier") @Nullable String runtimeIdentifier,
            /* Sets the mapping of internal animation controller references to actual animation controller. This is a JSON Array of name/animation-controller pairs */
            @JsonProperty("scripts") @Nullable Scripts scripts
        ) {
            
                /* Sets the mapping of internal animation controller references to actual animation controller. This is a JSON Array of name/animation-controller pairs */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Scripts(
                    /* Tells minecraft to run which animation / animation controllers and under what conditions. */
                    @JsonProperty("animate") @Nullable List<Object> animate
                ) {
                }
        }
}
