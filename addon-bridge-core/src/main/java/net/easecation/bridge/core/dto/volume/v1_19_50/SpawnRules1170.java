package net.easecation.bridge.core.dto.volume.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpawnRules1170(
    /* Specifies the version of the game this entity was made in. Minimum supported version is 1.17.0. Current supported version is 1.17.0. */
    @JsonProperty("format_version") String formatVersion,
    /* UNDOCUMENTED. */
    @JsonProperty("minecraft:volume") Minecraft_volume minecraft_volume
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Minecraft_volume(
            /* The description contains a single {@code identifier} string. */
            @JsonProperty("description") @Nullable Description description,
            /* UNDOCUMENTED. */
            @JsonProperty("components") @Nullable Components components
        ) {
            
                /* The description contains a single {@code identifier} string. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Description(
                    /* The unique identifier for this volume. It must be of the form {@code namespace:name', where namespace cannot be }minecraft`. */
                    @JsonProperty("identifier") @Nullable String identifier
                ) {
                }
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Components(
                    /* Component that defines a minimum and maximum block position for a bounding box and which world dimension the bounding box is in. Every volume must have a bounds component. */
                    @JsonProperty("minecraft:bounds") @Nullable Minecraft_bounds minecraft_bounds,
                    /* Displays the given fog whenever a player enters the volume. Each volume can only have one fog attached. */
                    @JsonProperty("minecraft:fog") @Nullable Minecraft_fog minecraft_fog,
                    /* Component that defines what happens when an actor enters the volume. Can contain multiple json objects. */
                    @JsonProperty("minecraft:on_actor_enter") @Nullable Minecraft_onActorEnter minecraft_onActorEnter,
                    /* Component that defines what happens when an actor leaves the volume. */
                    @JsonProperty("minecraft:on_actor_leave") @Nullable Minecraft_onActorLeave minecraft_onActorLeave
                ) {
                    
                        /* Component that defines a minimum and maximum block position for a bounding box and which world dimension the bounding box is in. Every volume must have a bounds component. */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record Minecraft_bounds(
                            /* The name of the dimension the bounding box will exist in: one of {@code overworld', }nether{@code  or }the end`. */
                            @JsonProperty("dimension") @Nullable String dimension,
                            /* The maximum block position of the bounding box. */
                            @JsonProperty("max") @Nullable List<Double> max,
                            /* The minimum block position of the bounding box. */
                            @JsonProperty("min") @Nullable List<Double> min
                        ) {
                        }
                    
                        /* Displays the given fog whenever a player enters the volume. Each volume can only have one fog attached. */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record Minecraft_fog(
                            /* The identifier of a fog definition. Note that you will not receive any feedback if the definition does not exist. */
                            @JsonProperty("fog_identifier") @Nullable String fogIdentifier,
                            /* The priority for this fog definition setting. Smaller numbers have higher priority. Fogs with equal priority will be combined together. */
                            @JsonProperty("priority") @Nullable Integer priority
                        ) {
                        }
                    
                        /* Component that defines what happens when an actor enters the volume. Can contain multiple json objects. */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record Minecraft_onActorEnter(
                            /* Required array that contains all the triggers. */
                            @JsonProperty("on_enter") List<Object> onEnter
                        ) {
                        }
                    
                        /* Component that defines what happens when an actor leaves the volume. */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record Minecraft_onActorLeave(
                            /* Required array that contains all the triggers. */
                            @JsonProperty("on_enter") List<Object> onEnter
                        ) {
                        }
                }
        }
}
