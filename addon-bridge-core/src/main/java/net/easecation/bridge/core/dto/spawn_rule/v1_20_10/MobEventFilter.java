package net.easecation.bridge.core.dto.spawn_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This component allows players to spawn mobs on a particular event. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MobEventFilter(
    /* The event String in this JSON Object is used to filter the spawn rules of the mob type. Can be type minecraft:pillager<i>patrols</i>event, minecraft:wandering<i>trader</i>event, or minecraft:ender<i>dragon</i>event.. */
    @JsonProperty("event") @Nullable String event
) {
}
