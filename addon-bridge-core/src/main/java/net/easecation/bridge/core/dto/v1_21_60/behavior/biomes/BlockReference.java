package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;
import javax.annotation.Nullable;

/* A minecraft block reference. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BlockReference {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BlockReference_Variant0(
        BlockIdentifier value
    ) implements BlockReference {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BlockReference_BlockReference(
        @JsonProperty("name") @Nullable BlockIdentifier name,
        @JsonProperty("states") @Nullable Map<String, Object> states,
        /* A condition using Molang queries that results to true/false that can be used to query for blocks with certain tags. */
        @JsonProperty("tags") @Nullable Molang tags
    ) implements BlockReference {}
}
