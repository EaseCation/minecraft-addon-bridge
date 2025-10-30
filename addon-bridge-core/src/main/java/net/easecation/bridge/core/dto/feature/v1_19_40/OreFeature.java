package net.easecation.bridge.core.dto.feature.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * {@code minecraft:ore<i>feature} places a vein of blocks to simulate ore deposits. Despite the name, any block can be placed by this feature. During placement, existing world blocks are checked to see if they can be replaced by the new ore block based on the list provided in the {@code may</i>replace} field of a {@code replace<i>rules} entry. If no {@code may</i>replace} field is specified in a {@code replace_rule} entry, the ore block can replace any existing block.
 * Succeeds if: At least one ore block is successfully placed.
 * Fails if: All ore block placements fail.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OreFeature(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* The number of blocks to be placed. */
    @JsonProperty("count") Double count,
    /* Collection of replace rules that will be checked in order of definition. If a rule is resolved, the rest will not be resolved for that block position. */
    @JsonProperty("replace_rules") @Nullable List<Object> replaceRules
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
