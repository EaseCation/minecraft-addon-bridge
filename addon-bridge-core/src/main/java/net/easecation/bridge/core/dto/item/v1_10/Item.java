package net.easecation.bridge.core.dto.item.v1_10;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Legacy模式物品主体
 * <p>
 * Legacy mode item body
 *
 * @author EaseCation
 * @since 2025-01-31
 */
public record Item(
    @JsonProperty("description") Description description,
    @JsonProperty("components") Components components
) {
}
