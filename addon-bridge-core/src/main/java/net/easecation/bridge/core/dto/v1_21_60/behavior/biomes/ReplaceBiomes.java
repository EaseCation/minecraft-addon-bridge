package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* Replaces a specified portion of one or more Minecraft biomes. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ReplaceBiomes(
    /* List of biome replacement configurations. Retroactively adding a new replacement to the front of this list will cause the world generation to change. Please add any new replacements to the end of the list. */
    @JsonProperty("replacements") List<Object> replacements
) {
}
