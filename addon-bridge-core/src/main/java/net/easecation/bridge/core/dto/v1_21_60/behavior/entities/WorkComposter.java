package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the NPC to use the composter POI to convert excess seeds into bone meal. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WorkComposter(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier,
    /* The amount of ticks the NPC will stay in their the work location. */
    @JsonProperty("active_time") @Nullable Integer activeTime,
    /* The maximum number of times the mob will interact with the composter. */
    @JsonProperty("block_interaction_max") @Nullable Integer blockInteractionMax,
    /* Determines whether the mob can empty a full composter. */
    @JsonProperty("can_empty_composter") @Nullable Boolean canEmptyComposter,
    /* Determines whether the mob can add items to a composter given that it is not full. */
    @JsonProperty("can_fill_composter") @Nullable Boolean canFillComposter,
    /* If true, this entity can work when their jobsite POI is being rained on. */
    @JsonProperty("can_work_in_rain") @Nullable Boolean canWorkInRain,
    /* The amount of ticks the goal will be on cooldown before it can be used again. */
    @JsonProperty("goal_cooldown") @Nullable Integer goalCooldown,
    /* The maximum number of items which can be added to the composter per block interaction. */
    @JsonProperty("items_per_use_max") @Nullable Integer itemsPerUseMax,
    /* Limits the amount of each compostable item the mob can use. Any amount held over this number will be composted if possible */
    @JsonProperty("min_item_count") @Nullable Integer minItemCount,
    /* Event to run when the mob reaches their jobsite. */
    @JsonProperty("on_arrival") @Nullable Trigger onArrival,
    /* Unused. */
    @JsonProperty("sound_delay_max") @Nullable Integer soundDelayMax,
    /* Unused. */
    @JsonProperty("sound_delay_min") @Nullable Integer soundDelayMin,
    /* The maximum interval in which the mob will interact with the composter. */
    @JsonProperty("use_block_max") @Nullable Integer useBlockMax,
    /* The minimum interval in which the mob will interact with the composter. */
    @JsonProperty("use_block_min") @Nullable Integer useBlockMin,
    /* If "can<i>work</i>in_rain" is false, this is the maximum number of ticks left in the goal where rain will not interrupt the goal */
    @JsonProperty("work_in_rain_tolerance") @Nullable Integer workInRainTolerance
) {
}
