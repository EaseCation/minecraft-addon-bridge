package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;

/* Additional variant value. Can be used to further differentiate variants. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MarkVariant(
    /* The ID of the variant. By convention, 0 is the ID of the base entity */
    @JsonProperty("value") Integer value
) {
}
