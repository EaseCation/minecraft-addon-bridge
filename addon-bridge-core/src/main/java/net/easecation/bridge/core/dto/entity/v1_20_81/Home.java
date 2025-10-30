package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Saves a home pos for when the the entity is spawned. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Home(
    /* The radius that the entity will be restricted to in relation to its home. */
    @JsonProperty("restriction_radius") @Nullable Integer restrictionRadius,
    /* Optional block list that the home position will be associated with. If any of the blocks no longer exist at that position, the home restriction is removed. Example syntax: minecraft:sand. Not supported: minecraft:sand:1 */
    @JsonProperty("home_block_list") @Nullable List<String> homeBlockList
) {
}
