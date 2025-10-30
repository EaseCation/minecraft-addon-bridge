package net.easecation.bridge.core.dto.block.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* A custom block definition. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlockDefinitions(
    /* The description for this block. */
    @JsonProperty("description") Description description,
    @JsonProperty("events") @Nullable Events events,
    @JsonProperty("components") Component components,
    /* UNDOCUMENTED. */
    @JsonProperty("permutations") @Nullable List<Object> permutations
) {
    
        /* The description for this block. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The category this block will be placed in the menu. */
            @JsonProperty("menu_category") @Nullable MenuCategory menuCategory,
            /* The identifier for this block. The name must include a namespace and must not use the Minecraft namespace unless overriding a Vanilla block. */
            @JsonProperty("identifier") String identifier,
            /* If this block is experimental, it will only be registered if the world is marked as experimantal. */
            @JsonProperty("is_experimental") @Nullable Boolean isExperimental,
            /* Whether or not to register this block to the creative inventory menu. */
            @JsonProperty("register_to_creative_menu") @Nullable Boolean registerToCreativeMenu,
            /* UNDOCUMENTED. */
            @JsonProperty("properties") @Nullable Map<String, Object> properties
        ) {
            
                /* The category this block will be placed in the menu. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record MenuCategory(
                    /* Determines which category this block will be placed under in the inventory and crafting table container screens. Options are "construction", "nature", "equipment", "items", and "none". If omitted or "none" is specified, the block will not appear in the inventory or crafting table container screens. */
                    @JsonProperty("category") String category,
                    /* A translation string of an existing group in minecraft to group this item under */
                    @JsonProperty("group") @Nullable String group,
                    /* If true, this item will not be shown in the /give command autocomplete list. */
                    @JsonProperty("is_hidden_in_commands") @Nullable Boolean isHiddenInCommands
                ) {
                }
        }
}
