package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests for the presence of a damaged named item in the designated slot of the subject entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasDamagedEquipment(
    /* Tests for the presence of a damaged named item in the designated slot of the subject entity. */
    @JsonProperty("test") @Nullable String test,
    /* The equipment location to test. */
    @JsonProperty("domain") @Nullable String domain,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The item name to look for. */
    @JsonProperty("value") ItemIdentifier value
) {
}
