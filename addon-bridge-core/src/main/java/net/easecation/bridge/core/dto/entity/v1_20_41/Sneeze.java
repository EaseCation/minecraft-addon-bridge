package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to stop and sneeze possibly startling nearby mobs and dropping an item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Sneeze(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* The probability that the mob will drop an item when it sneezes. */
    @JsonProperty("drop_item_chance") @Nullable Double dropItemChance,
    /* List of entity types this mob will startle (cause to jump) when it sneezes. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* Loot table to select dropped items from. */
    @JsonProperty("loot_table") @Nullable String lootTable,
    /* Sound to play when the sneeze is about to happen. */
    @JsonProperty("prepare_sound") @Nullable String prepareSound,
    /* The time in seconds that the mob takes to prepare to sneeze (while the prepare_sound is playing). */
    @JsonProperty("prepare_time") @Nullable Double prepareTime,
    /* The probability of sneezing. A value of 1.00 is 100% */
    @JsonProperty("probability") @Nullable Double probability,
    /* Sound to play when the sneeze occurs. */
    @JsonProperty("sound") @Nullable String sound,
    /* Distance in blocks that mobs will be startled. */
    @JsonProperty("within_radius") @Nullable Double withinRadius
) {
}
