package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.processors;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface DBlockSpecifier {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record DBlockSpecifier_Variant0(
    ) implements DBlockSpecifier {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record DBlockSpecifier_Variant1(
        @JsonProperty("name") @Nullable String name,
        @JsonProperty("states") @Nullable Map<String, Object> states
    ) implements DBlockSpecifier {
    }
}
