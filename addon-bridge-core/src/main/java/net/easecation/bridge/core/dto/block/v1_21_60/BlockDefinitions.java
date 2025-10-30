package net.easecation.bridge.core.dto.block.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* A custom block definition. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlockDefinitions(
    /* The description for this block. */
    @JsonProperty("description") Description description,
    @JsonProperty("components") Component components,
    /* UNDOCUMENTED. */
    @JsonProperty("permutations") @Nullable List<Object> permutations
) {
    
        /* The description for this block. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The identifier for this block. The name must include a namespace and must not use the Minecraft namespace unless overriding a Vanilla block. */
            @JsonProperty("identifier") String identifier,
            /* Specifies the menu category and group for the block, which determine where this block is placed in the inventory and crafting table container screens. If this field is omitted, the block will not appear in the inventory or crafting table container screens. */
            @JsonProperty("menu_category") @Nullable MenuCategory menuCategory,
            @JsonProperty("states") @Nullable Map<String, StatesValue> states,
            @JsonProperty("traits") @Nullable Traits traits
        ) {
            
                /* Specifies the menu category and group for the block, which determine where this block is placed in the inventory and crafting table container screens. If this field is omitted, the block will not appear in the inventory or crafting table container screens. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record MenuCategory(
                    @JsonProperty("category") String category,
                    @JsonProperty("group") @Nullable String group,
                    /* Determines whether or not this item can be used with commands such as /give and /setblock. Commands can use blocks by default */
                    @JsonProperty("is_hidden_in_commands") @Nullable Boolean isHiddenInCommands
                ) {
                }
        }
}
