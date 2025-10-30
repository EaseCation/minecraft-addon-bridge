package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* The components groups to add or remove. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AddOrRemove(
    /* The components groups to add or remove. */
    @JsonProperty("component_groups") @Nullable List<String> componentGroups
) {
}
