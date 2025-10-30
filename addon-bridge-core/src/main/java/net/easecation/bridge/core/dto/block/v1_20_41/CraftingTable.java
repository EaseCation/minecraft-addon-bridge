package net.easecation.bridge.core.dto.block.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* [Experimental] Makes your block into a custom crafting table which enables the crafting table UI and the ability to craft recipes. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CraftingTable(
    /* Defines the tags recipes should define to be crafted on this table. Limited to 64 tags. Each tag is limited to 64 characters. */
    @JsonProperty("crafting_tags") @Nullable List<String> craftingTags,
    /* Specifies the language file key that maps to what text will be displayed in the UI of this table. If the string given can not be resolved as a loc string, the raw string given will be displayed. If this field is omitted, the name displayed will default to the name specified in the "display<i>name" component. If this block has no "display</i>name" component, the name displayed will default to the name of the block. */
    @JsonProperty("table_name") @Nullable String tableName
) {
}
