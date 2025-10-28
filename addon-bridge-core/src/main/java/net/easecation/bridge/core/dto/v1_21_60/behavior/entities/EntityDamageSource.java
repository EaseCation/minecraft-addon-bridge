package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

/* The types of damage an entity can receive. */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EntityDamageSource {
    @JsonProperty("all") ALL,
    @JsonProperty("anvil") ANVIL,
    @JsonProperty("block_explosion") BLOCK_EXPLOSION,
    @JsonProperty("campfire") CAMPFIRE,
    @JsonProperty("charging") CHARGING,
    @JsonProperty("contact") CONTACT,
    @JsonProperty("drowning") DROWNING,
    @JsonProperty("entity_attack") ENTITY_ATTACK,
    @JsonProperty("entity_explosion") ENTITY_EXPLOSION,
    @JsonProperty("fall") FALL,
    @JsonProperty("falling_block") FALLING_BLOCK,
    @JsonProperty("fire") FIRE,
    @JsonProperty("fire_tick") FIRE_TICK,
    @JsonProperty("fireworks") FIREWORKS,
    @JsonProperty("fly_into_wall") FLY_INTO_WALL,
    @JsonProperty("freezing") FREEZING,
    @JsonProperty("lava") LAVA,
    @JsonProperty("lightning") LIGHTNING,
    @JsonProperty("magic") MAGIC,
    @JsonProperty("magma") MAGMA,
    @JsonProperty("none") NONE,
    @JsonProperty("override") OVERRIDE,
    @JsonProperty("piston") PISTON,
    @JsonProperty("projectile") PROJECTILE,
    @JsonProperty("ram_attack") RAM_ATTACK,
    @JsonProperty("self_destruct") SELF_DESTRUCT,
    @JsonProperty("sonic_boom") SONIC_BOOM,
    @JsonProperty("soul_campfire") SOUL_CAMPFIRE,
    @JsonProperty("stalactite") STALACTITE,
    @JsonProperty("stalagmite") STALAGMITE,
    @JsonProperty("starve") STARVE,
    @JsonProperty("suffocation") SUFFOCATION,
    @JsonProperty("temperature") TEMPERATURE,
    @JsonProperty("thorns") THORNS,
    @JsonProperty("void") VOID,
    @JsonProperty("wither") WITHER
}
