package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* Cool down time for a component. After you use an item it becomes unusable for the duration specified by the {@code cool down time} setting in this component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Cooldown(
    /* The type of cool down for this item. */
    @JsonProperty("category") String category,
    /* The duration of time this item will spend cooling down before becoming usable again. */
    @JsonProperty("duration") Double duration
) {
}
