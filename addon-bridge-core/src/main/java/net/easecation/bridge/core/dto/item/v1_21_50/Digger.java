package net.easecation.bridge.core.dto.item.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Digger item. Component put on items that dig. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Digger(
    /* Toggles if the item will be used efficiently. */
    @JsonProperty("use_efficiency") @Nullable Boolean useEfficiency,
    /* Destroy speed per block. */
    @JsonProperty("destroy_speeds") List<Object> destroySpeeds
) {
}
