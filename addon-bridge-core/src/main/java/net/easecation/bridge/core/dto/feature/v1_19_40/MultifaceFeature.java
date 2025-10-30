package net.easecation.bridge.core.dto.feature.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * {@code minecraft:multiface<i>feature} places one or a few multiface blocks on floors/walls/ceilings. Despite the name, any block can be placed by this feature. During placement, existing world blocks are checked to see if this feature can be placed on them based on the list provided in the {@code can</i>place<i>on} field. If no {@code can</i>replace<i>on} field is specified, the {@code place</i>block} block can be placed on any existing block.
 * This feature will also try to spread the {@code place_block} block around the location in world the feature is placed.
 * Succeeds if: At least one block is successfully placed.
 * Fails if: All block placements fail.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MultifaceFeature(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* Reference to the block to be placed. */
    @JsonProperty("places_block") String placesBlock,
    /* How far, in blocks, this feature can search for a valid position to place. */
    @JsonProperty("search_range") Integer searchRange,
    /* Can this feature be placed on the ground (top face of a block)?. */
    @JsonProperty("can_place_on_floor") Boolean canPlaceOnFloor,
    /* Can this feature be placed on the ceiling (bottom face of a block)?. */
    @JsonProperty("can_place_on_ceiling") Boolean canPlaceOnCeiling,
    /* Can this feature be placed on the wall (side faces of a block)?. */
    @JsonProperty("can_place_on_wall") Boolean canPlaceOnWall,
    /* For each block placed by this feature, how likely will that block spread to another?. */
    @JsonProperty("chance_of_spreading") Double chanceOfSpreading,
    /* How far, in blocks, this feature can search for a valid position to place. */
    @JsonProperty("can_place_on") @Nullable List<String> canPlaceOn
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
