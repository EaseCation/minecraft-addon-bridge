package net.easecation.bridge.core.dto.trading.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TradingDefinition(
    @JsonProperty("format_version") @Nullable String formatVersion,
    /* A collection of tiers. */
    @JsonProperty("tiers") @Nullable List<Object> tiers
) {
}
