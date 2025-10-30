package net.easecation.bridge.core.dto.feature.v1_20_81;

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
    @JsonProperty("description") Description description,
    /* Reference to the block to be placed. */
    @JsonProperty("places_block") String placesBlock,
    /* If true, enforce the block's canPlace check. */
    @JsonProperty("enforce_placement_rules") Boolean enforcePlacementRules,
    /* If true, enforce the block's canSurvive check. */
    @JsonProperty("enforce_survivability_rules") Boolean enforceSurvivabilityRules,
    /* The list of valid block and block faces the given block may attach to when being placed. */
    @JsonProperty("may_attach_to") @Nullable MayAttachTo mayAttachTo,
    /* A list of blocks that may be replaced during placement. Omit this field to allow any block to be replaced. */
    @JsonProperty("may_replace") @Nullable List<String> mayReplace
) {
    
        /* The list of valid block and block faces the given block may attach to when being placed. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MayAttachTo(
            /* Minimum number of sides that must be attached when being placed. */
            @JsonProperty("min_sides_must_attach") @Nullable Object minSidesMustAttach,
            /* Automatically rotate the block to attach sensibly. */
            @JsonProperty("auto_rotate") @Nullable Boolean autoRotate,
            /* UNDOCUMENTED. */
            @JsonProperty("top") @Nullable BiBlockSide top,
            /* UNDOCUMENTED. */
            @JsonProperty("bottom") @Nullable BiBlockSide bottom,
            /* UNDOCUMENTED. */
            @JsonProperty("north") @Nullable BiBlockSide north,
            /* UNDOCUMENTED. */
            @JsonProperty("south") @Nullable BiBlockSide south,
            /* UNDOCUMENTED. */
            @JsonProperty("east") @Nullable BiBlockSide east,
            /* UNDOCUMENTED. */
            @JsonProperty("west") @Nullable BiBlockSide west,
            /* UNDOCUMENTED. */
            @JsonProperty("all") @Nullable BiBlockSide all,
            /* UNDOCUMENTED. */
            @JsonProperty("sides") @Nullable BiBlockSide sides
        ) {
        }
}
