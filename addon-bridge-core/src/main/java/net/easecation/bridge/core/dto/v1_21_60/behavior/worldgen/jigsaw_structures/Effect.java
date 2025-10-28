package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

/* A mob effect */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Effect {
    @JsonProperty("absorption") ABSORPTION,
    @JsonProperty("bad_omen") BAD_OMEN,
    @JsonProperty("blindness") BLINDNESS,
    @JsonProperty("conduit_power") CONDUIT_POWER,
    @JsonProperty("darkness") DARKNESS,
    @JsonProperty("fatal_poison") FATAL_POISON,
    @JsonProperty("fire_resistance") FIRE_RESISTANCE,
    @JsonProperty("haste") HASTE,
    @JsonProperty("health_boost") HEALTH_BOOST,
    @JsonProperty("hunger") HUNGER,
    @JsonProperty("infested") INFESTED,
    @JsonProperty("instant_damage") INSTANT_DAMAGE,
    @JsonProperty("instant_health") INSTANT_HEALTH,
    @JsonProperty("invisibility") INVISIBILITY,
    @JsonProperty("jump_boost") JUMP_BOOST,
    @JsonProperty("levitation") LEVITATION,
    @JsonProperty("mining_fatigue") MINING_FATIGUE,
    @JsonProperty("nausea") NAUSEA,
    @JsonProperty("night_vision") NIGHT_VISION,
    @JsonProperty("oozing") OOZING,
    @JsonProperty("poison") POISON,
    @JsonProperty("raid_omen") RAID_OMEN,
    @JsonProperty("regeneration") REGENERATION,
    @JsonProperty("resistance") RESISTANCE,
    @JsonProperty("saturation") SATURATION,
    @JsonProperty("slow_falling") SLOW_FALLING,
    @JsonProperty("slowness") SLOWNESS,
    @JsonProperty("speed") SPEED,
    @JsonProperty("strength") STRENGTH,
    @JsonProperty("trial_omen") TRIAL_OMEN,
    @JsonProperty("village_hero") VILLAGE_HERO,
    @JsonProperty("water_breathing") WATER_BREATHING,
    @JsonProperty("weakness") WEAKNESS,
    @JsonProperty("weaving") WEAVING,
    @JsonProperty("wind_charged") WIND_CHARGED,
    @JsonProperty("wither") WITHER
}
