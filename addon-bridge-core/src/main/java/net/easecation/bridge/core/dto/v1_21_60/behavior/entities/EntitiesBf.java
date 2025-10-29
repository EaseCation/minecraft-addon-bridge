package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface EntitiesBf {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntitiesBf_Variant0(
        List<Object> value
    ) implements EntitiesBf {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION) 
    record EntitiesBf_Variant1(
    ) implements EntitiesBf {
    }
}
