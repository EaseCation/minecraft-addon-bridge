package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Could increase crop growth when entity walks over crop. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GrowsCrop(
    /* Value between 0-1. Chance of success per tick. */
    @JsonProperty("chance") @Nullable Double chance,
    /* Number of charges. */
    @JsonProperty("charges") @Nullable Integer charges
) {
}
