package net.easecation.bridge.core.dto.trading.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* The function set<i>book</i>contents. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetBookContents(
    /* UNDOCUMENTED. */
    @JsonProperty("function") String function,
    /* UNDOCUMENTED. */
    @JsonProperty("author") String author,
    /* UNDOCUMENTED. */
    @JsonProperty("title") String title,
    /* UNDOCUMENTED. */
    @JsonProperty("pages") List<String> pages
) {
}
