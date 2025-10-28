package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the designated equipment location for the subject entity has any empty slot. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AnySlotsEmpty(
    /* Returns true when the designated equipment location for the subject entity has any empty slot. */
    @JsonProperty("test") @Nullable String test,
    /* (Optional) The comparison to apply with {@code value}. */
    @JsonProperty("operator") @Nullable Operator operator,
    /* (Optional) The subject of this filter test. */
    @JsonProperty("subject") @Nullable Subject subject,
    /* The equipment location to test. */
    @JsonProperty("value") EquipmentLocation value
) {
}
