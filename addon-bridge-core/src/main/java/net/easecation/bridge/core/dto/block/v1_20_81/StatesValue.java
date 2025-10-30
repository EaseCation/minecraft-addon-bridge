package net.easecation.bridge.core.dto.block.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* The key defines the name of a state, which must be properly namespaced. Each value is an array that contains all of the possible values of that state or an object defining a range of integers. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface StatesValue {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record StatesValue_Variant0(
        List<Object> value
    ) implements StatesValue {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record StatesValue_Variant1(
        @JsonProperty("values") Values values
    ) implements StatesValue {
        
            @JsonIgnoreProperties(ignoreUnknown = true)
            public record Values(
                /* The lowest integer this state supports. This is also used as the default state value. */
                @JsonProperty("min") Integer min,
                /* The highest integer this state supports. This cannot be more than 15 above the minimum. */
                @JsonProperty("max") Integer max
            ) {
            }
    }
}
