package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to attack the player by summoning other entities. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SummonEntity(
    @JsonProperty("priority") @Nullable Integer priority,
    /* List of spells for the mob to use to summon entities. */
    @JsonProperty("summon_choices") @Nullable List<Object> summonChoices
) {
}
