package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines a set of conditions under which an entity should take damage. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HurtOnCondition(
    /* An array of conditions used to compare the event to. */
    @JsonProperty("damage_conditions") @Nullable List<Object> damageConditions
) {
}
