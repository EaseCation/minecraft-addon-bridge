package net.easecation.bridge.core.dto.item.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* The tags component determines which tags an item has on it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Tags(
    /* An array that can contain multiple item tags. */
    @JsonProperty("tags") List<Object> tags
) {
}
