package net.easecation.bridge.core.dto.item.v1_19_50;

import com.fasterxml.jackson.annotation.*;

/* The armor item component determines the amount of protection you have in your armor item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Armor(
    /* How much protection does the armor item have. */
    @JsonProperty("protection") Integer protection,
    /* Texture Type to apply for the armor. Note that Horse armor is restricted to leather, iron, gold, or diamond. */
    @JsonProperty("texture_type") String textureType
) {
}
