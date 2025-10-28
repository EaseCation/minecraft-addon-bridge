package net.easecation.bridge.core.dto.v1_21_60.behavior.animations;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Animationspec {
    /* A single string that specifies which animation there are. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Animationspec_AnimationSpecification(
        /* A single string that specifies which animation there are. */
        String value
    ) implements Animationspec {}
    /* A object specification on how to transition. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Animationspec_AnimationSpecification0(
    ) implements Animationspec {}
}
