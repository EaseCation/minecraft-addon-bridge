package net.easecation.bridge.core.dto.v1_21_60.behavior.item_catalog;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Represents a group of items. A group can be collapsible if it is provided with a group identifier, or added a set of loose items without an icon. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Group(
    /* Optional field to give an icon and name to a group. Otherwise the items are added as loose items. If there are two groups with the same name within a category, they will merge. */
    @JsonProperty("group_identifier") @Nullable GroupIdentifier groupIdentifier,
    /* List of items to be added */
    @JsonProperty("items") List<Object> items
) {
    
        /* Optional field to give an icon and name to a group. Otherwise the items are added as loose items. If there are two groups with the same name within a category, they will merge. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record GroupIdentifier(
            /* The item or block that represents the group. */
            @JsonProperty("icon") @Nullable String icon,
            /* The localization string that is display when you hover over your group name. The localization string needs to include the namespace. */
            @JsonProperty("name") String name
        ) {
        }
}
