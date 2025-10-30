package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to try to move to air once it is close to running out of its total breathable supply. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SwimUpForBreath(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The material the mob is traveling in. An air block will only be considered valid to move to with a block of this material below it. */
    @JsonProperty("material_type") @Nullable String materialType,
    /* The height (in blocks) above the mob's current position that it will search for a valid air block to move to. If a valid block cannot be found, the mob will move to the position this many blocks above it. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* The radius (in blocks) around the mob's current position that it will search for a valid air block to move to. */
    @JsonProperty("search_radius") @Nullable Integer searchRadius,
    /* Movement speed multiplier of the mob when using this Goal. */
    @JsonProperty("speed_mod") @Nullable Double speedMod
) {
}
