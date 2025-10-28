package net.easecation.bridge.core.dto.v1_21_60.behavior.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum IdEnchantment {
    @JsonProperty("aqua_affinity") AQUA_AFFINITY,
    @JsonProperty("bane_of_arthropods") BANE_OF_ARTHROPODS,
    @JsonProperty("blast_protection") BLAST_PROTECTION,
    @JsonProperty("channeling") CHANNELING,
    @JsonProperty("binding") BINDING,
    @JsonProperty("curse_of_vanishing") CURSE_OF_VANISHING,
    @JsonProperty("depth_strider") DEPTH_STRIDER,
    @JsonProperty("efficiency") EFFICIENCY,
    @JsonProperty("feather_falling") FEATHER_FALLING,
    @JsonProperty("fire_aspect") FIRE_ASPECT,
    @JsonProperty("fire_protection") FIRE_PROTECTION,
    @JsonProperty("flame") FLAME,
    @JsonProperty("fortune") FORTUNE,
    @JsonProperty("frost_walker") FROST_WALKER,
    @JsonProperty("impaling") IMPALING,
    @JsonProperty("infinity") INFINITY,
    @JsonProperty("knockback") KNOCKBACK,
    @JsonProperty("looting") LOOTING,
    @JsonProperty("loyalty") LOYALTY,
    @JsonProperty("luck_of_the_sea") LUCK_OF_THE_SEA,
    @JsonProperty("lure") LURE,
    @JsonProperty("mending") MENDING,
    @JsonProperty("multishot") MULTISHOT,
    @JsonProperty("piercing") PIERCING,
    @JsonProperty("projectile_protection") PROJECTILE_PROTECTION,
    @JsonProperty("protection") PROTECTION,
    @JsonProperty("power") POWER,
    @JsonProperty("punch") PUNCH,
    @JsonProperty("quick_charge") QUICK_CHARGE,
    @JsonProperty("respiration") RESPIRATION,
    @JsonProperty("riptide") RIPTIDE,
    @JsonProperty("sharpness") SHARPNESS,
    @JsonProperty("silk_touch") SILK_TOUCH,
    @JsonProperty("smite") SMITE,
    @JsonProperty("soul_speed") SOUL_SPEED,
    @JsonProperty("thorns") THORNS,
    @JsonProperty("unbreaking") UNBREAKING
}
