package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the target has any trade supply left. Will return false if the target cannot be traded with. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasTradeSupply(
    /* Tests whether the target has any trade supply left. Will return false if the target cannot be traded with. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
