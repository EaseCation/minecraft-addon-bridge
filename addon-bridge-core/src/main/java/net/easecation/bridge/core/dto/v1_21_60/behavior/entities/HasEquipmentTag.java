package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests for the presence of an item with the named tag in the designated slot of the subject entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasEquipmentTag(
    /* Tests for the presence of an item with the named tag in the designated slot of the subject entity. */
    @JsonProperty("test") @Nullable String test,
    /* The equipment location to test. */
    @JsonProperty("domain") @Nullable String domain,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The item name to look for. */
    @JsonProperty("value") ItemIdentifier value
) {
}
