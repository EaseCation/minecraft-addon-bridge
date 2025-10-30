package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the entity to consume a block, replace the eaten block with another block, and trigger an event as a result. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EatBlock(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The event to trigger when the block eating animation has completed. */
    @JsonProperty("on_eat") @Nullable Trigger onEat,
    /* A molang expression defining the success chance the entity has to consume a block. */
    @JsonProperty("success_chance") @Nullable MolangNumber successChance,
    /* The amount of time (in seconds) it takes for the block to be eaten upon a successful eat attempt. */
    @JsonProperty("time_until_eat") @Nullable Double timeUntilEat,
    /* A collection of pairs of blocks; the first ("eat<i>block")is the block the entity should eat, the second ("replace</i>block") is the block that should replace the eaten block. */
    @JsonProperty("eat_and_replace_block_pairs") @Nullable List<Object> eatAndReplaceBlockPairs
) {
}
