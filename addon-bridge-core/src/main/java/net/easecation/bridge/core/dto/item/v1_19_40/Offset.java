package net.easecation.bridge.core.dto.item.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Offset(
    /* UNDOCUMENTED. */
    @JsonProperty("first_person") @Nullable Mode firstPerson,
    /* UNDOCUMENTED. */
    @JsonProperty("thrid_person") @Nullable Mode thridPerson
) {
}
