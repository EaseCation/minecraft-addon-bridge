package net.easecation.bridge.core.dto.loot_table.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets a random chance of the specified value based on the level difficulty. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomDifficultyChance(
    /* UNDOCUMENTED. */
    @JsonProperty("condition") @Nullable String condition,
    /* The default random chance if the level difficulty is not assigned. */
    @JsonProperty("default_chance") @Nullable Double defaultChance,
    /* The default random chance if the level difficulty is in easy. Omitting this field will set the value to {@code default_chance} field. */
    @JsonProperty("easy") @Nullable Double easy,
    /* The default random chance if the level difficulty is in hard. Omitting this field will set the value to {@code default_chance} field. */
    @JsonProperty("hard") @Nullable Double hard,
    /* The default random chance if the level difficulty is in normal. Omitting this field will set the value to {@code default_chance} field. */
    @JsonProperty("normal") @Nullable Double normal,
    /* The default random chance if the level difficulty is in peaceful. Omitting this field will set the value to {@code default_chance} field. */
    @JsonProperty("peaceful") @Nullable Double peaceful
) {
}
