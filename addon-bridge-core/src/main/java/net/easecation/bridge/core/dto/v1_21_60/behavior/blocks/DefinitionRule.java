package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* The definition rule that specifies the behavior for one liquid type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DefinitionRule(
    /* Whether this block can contain the liquid. For example, if the liquid type is water, this means the block can be waterlogged. */
    @JsonProperty("can_contain_liquid") @Nullable Boolean canContainLiquid,
    /* The type of liquid this detection rule is for. */
    @JsonProperty("liquid_type") @Nullable String liquidType,
    /*
 * How the block reacts to flowing water. Must be one of the following options:
 * "blocking" - The default value for this field. The block stops the liquid from flowing.
 * "broken" - The block is destroyed completely.
 * "popped" - The block is destroyed and its item is spawned.
 * "no_reaction" - The block is unaffected; visually, the liquid will flow through the block.
 */
    @JsonProperty("on_liquid_touches") @Nullable String onLiquidTouches,
    /* When a block contains a liquid, controls the directions in which the liquid can't flow out from the block. Also controls the directions in which a block can stop liquid flowing into it if no<i>reaction is set for the on</i>liquid_touches field. The default is an empty list; this means that liquid can flow out of all directions by default. */
    @JsonProperty("stops_liquid_flowing_from_direction") @Nullable List<String> stopsLiquidFlowingFromDirection
) {
}
