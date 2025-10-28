package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Item(
    /* The description for this item */
    @JsonProperty("description") Description description,
    /* The components of this item. */
    @JsonProperty("components") @Nullable Map<String, Object> components
) {
    
        /* The description for this item */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The identifier for this item. The name must include a namespace and must not use the Minecraft namespace unless overriding a Vanilla item. */
            @JsonProperty("identifier") @Nullable String identifier,
            /* If this item is experimental, it will only be registered if the world is marked as experimental. */
            @JsonProperty("is_experimental") @Nullable Boolean isExperimental,
            /* The Creative Category that includes the specified item. */
            @JsonProperty("menu_category") @Nullable MenuCategory menuCategory
        ) {
            
                /* The Creative Category that includes the specified item. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record MenuCategory(
                    /* A translation string of an existing group in minecraft to group this block/item under */
                    @JsonProperty("group") @Nullable String group,
                    @JsonProperty("category") @Nullable Category category,
                    /* Determines whether or not this item can be used with commands. Commands can use items by default */
                    @JsonProperty("is_hidden_in_commands") @Nullable Boolean isHiddenInCommands
                ) {
                }
        }
}
