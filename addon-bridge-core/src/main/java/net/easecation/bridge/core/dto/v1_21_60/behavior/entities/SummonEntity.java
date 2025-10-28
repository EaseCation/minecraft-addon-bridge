package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to attack the player by summoning other entities. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SummonEntity(
    @JsonProperty("priority") @Nullable Priority priority,
    /* List of spells for the mob to use to summon entities. */
    @JsonProperty("summon_choices") @Nullable List<Object> summonChoices
) {
}
