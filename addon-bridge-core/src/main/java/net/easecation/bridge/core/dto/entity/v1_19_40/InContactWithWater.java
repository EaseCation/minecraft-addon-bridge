package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity in contact with any water: water, rain, splash water bottle. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InContactWithWater(
    /* Returns true when the subject entity in contact with any water: water, rain, splash water bottle. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* (Optional) true or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
