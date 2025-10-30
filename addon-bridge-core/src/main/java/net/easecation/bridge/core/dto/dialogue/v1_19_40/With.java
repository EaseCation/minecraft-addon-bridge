package net.easecation.bridge.core.dto.dialogue.v1_19_40;

import com.fasterxml.jackson.annotation.*;

/* Specifies for the translator that additional text component needs to be inserted, this will cause the translator to look for variables in the translation text and replaced them with the corresponding 'With' text component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record With(
) {
}
