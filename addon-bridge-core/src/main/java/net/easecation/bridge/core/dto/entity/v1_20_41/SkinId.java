package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Skin ID value. Can be used to differentiate skins, such as base skins for villagers. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SkinId(
    /* The ID of the skin. By convention, 0 is the ID of the base skin */
    @JsonProperty("value") @Nullable Integer value
) {
}
