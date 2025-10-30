package net.easecation.bridge.core.dto.feature.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * {@code minecraft:single<i>block</i>feature} places a single block in the world. The {@code may<i>place</i>on} and {@code may<i>replace} fields are allowlists which specify where the block can be placed. If these fields are omitted, the block can be placed anywhere. The block's internal survivability and placement rules can optionally be enforced with the {@code enforce</i>survivability<i>rules} and {@code enforce</i>placement_rules} fields. These rules are specified per-block and are typically designed to produce high quality gameplay or natural behavior. However, enabling this enforcement may make it harder to debug placement failures.
 *  Succeeds if: The block is successfully placed in the world.
 *  Fails if: The block fails to be placed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SingleBlockFeature(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* Reference to the block to be placed. */
    @JsonProperty("places_block") String placesBlock,
    /* If true, enforce the block's canPlace check. */
    @JsonProperty("enforce_placement_rules") Boolean enforcePlacementRules,
    /* If true, enforce the block's canSurvive check. */
    @JsonProperty("enforce_survivability_rules") Boolean enforceSurvivabilityRules,
    /* UNDOCUMENTED. */
    @JsonProperty("may_attach_to") @Nullable MayAttachTo mayAttachTo,
    /* A list of blocks that may be replaced during placement. Omit this field to allow any block to be replaced. */
    @JsonProperty("may_replace") @Nullable List<String> mayReplace
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MayAttachTo(
            /* UNDOCUMENTED. */
            @JsonProperty("min_sides_must_attach") @Nullable Object minSidesMustAttach,
            /* Automatically rotate the block to attach sensibly. */
            @JsonProperty("auto_rotate") @Nullable Object autoRotate,
            /* UNDOCUMENTED. */
            @JsonProperty("top") @Nullable BjBlockSide top,
            /* UNDOCUMENTED. */
            @JsonProperty("bottom") @Nullable BjBlockSide bottom,
            /* UNDOCUMENTED. */
            @JsonProperty("north") @Nullable BjBlockSide north,
            /* UNDOCUMENTED. */
            @JsonProperty("south") @Nullable BjBlockSide south,
            /* UNDOCUMENTED. */
            @JsonProperty("east") @Nullable BjBlockSide east,
            /* UNDOCUMENTED. */
            @JsonProperty("west") @Nullable BjBlockSide west,
            /* UNDOCUMENTED. */
            @JsonProperty("all") @Nullable BjBlockSide all,
            /* UNDOCUMENTED. */
            @JsonProperty("sides") @Nullable BjBlockSide sides
        ) {
        }
}
