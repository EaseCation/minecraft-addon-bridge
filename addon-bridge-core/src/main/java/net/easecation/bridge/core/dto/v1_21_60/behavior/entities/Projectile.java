package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* Allows the entity to be a thrown entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Projectile(
    /* Allows you to choose an anchor point for where the projectile is fired from. 0 = Original point, 1 = EyeHeight, and 2 = Middle or body height. */
    @JsonProperty("anchor") @Nullable Integer anchor,
    /* Alters the angle at which a projectile is vertically shot. Many splash potions in the game use this to offset their angles by -20 degrees. */
    @JsonProperty("angle_offset") @Nullable Double angleOffset,
    /* If true, the entity hit will be set on fire. */
    @JsonProperty("catch_fire") @Nullable Boolean catchFire,
    /* If true, when a projectile deals damage, whether or not to spawn in the critical damage particles. */
    @JsonProperty("crit_particle_on_hurt") @Nullable Boolean critParticleOnHurt,
    /* When this projectile deals damage, whether or not to immediately destroy this projectile. */
    @JsonProperty("destroy_on_hurt") @Nullable Boolean destroyOnHurt,
    /* Entity Definitions defined here can't be hurt by the projectile. */
    @JsonProperty("filter") @Nullable String filter,
    /* If true, whether the projectile causes fire is affected by the mob griefing game rule. */
    @JsonProperty("fire_affected_by_griefing") @Nullable Boolean fireAffectedByGriefing,
    /* The gravity applied to this entity when thrown. When this actor is not on the ground, subtracts this amount from the actors change in vertical position every tick. The higher the value, the faster the entity falls. */
    @JsonProperty("gravity") @Nullable Double gravity,
    /* If true, when hitting a vehicle, and there's at least one passenger in the vehicle, the damage will be dealt to the passenger closest to the projectile impact point. If there are no passengers, this setting does nothing. */
    @JsonProperty("hit_nearest_passenger") @Nullable Boolean hitNearestPassenger,
    /* [EXPERIMENTAL] An array of strings defining the types of entities that this entity does not collide with. */
    @JsonProperty("ignored_entities") @Nullable List<String> ignoredEntities,
    /* The sound that plays when the projectile hits the ground. */
    @JsonProperty("hit_ground_sound") @Nullable SoundEvent hitGroundSound,
    /* The sound that plays when the projectile hits something. */
    @JsonProperty("hit_sound") @Nullable SoundEvent hitSound,
    /* If true, the projectile homes in to the nearest entity. */
    @JsonProperty("homing") @Nullable Boolean homing,
    /* The fraction of the projectile's speed maintained every frame while traveling in air. */
    @JsonProperty("inertia") @Nullable Double inertia,
    /* If true, the projectile will be treated as dangerous to the players. */
    @JsonProperty("is_dangerous") @Nullable Boolean isDangerous,
    /* If true, the projectile will knock back the entity it hits. */
    @JsonProperty("knockback") @Nullable Boolean knockback,
    /* If true, the entity hit will be struck by lightning. */
    @JsonProperty("lightning") @Nullable Boolean lightning,
    /* The fraction of the projectile's speed maintained every frame while traveling in water. */
    @JsonProperty("liquid_inertia") @Nullable Double liquidInertia,
    /* If true, the projectile can hit multiple entities per flight. */
    @JsonProperty("multiple_targets") @Nullable Boolean multipleTargets,
    /* SEE on<i>hit/mob</i>effect. */
    @JsonProperty("mob_effect") @Nullable Object mobEffect,
    /* The offset from the entity's anchor where the projectile will spawn. */
    @JsonProperty("offset") @Nullable VectorOf3Items offset,
    /* Time in seconds that the entity hit will be on fire for. */
    @JsonProperty("on_fire_time") @Nullable Double onFireTime,
    /* Defines the behaviors that may execute on a projectile's hit, including impact damage, impact effect, and stuck in ground. See more on these parameters below. */
    @JsonProperty("on_hit") @Nullable OnHit onHit,
    /* Particle to use upon collision. */
    @JsonProperty("particle") @Nullable String particle,
    /* Defines the effect the arrow will apply to the entity it hits. */
    @JsonProperty("potion_effect") @Nullable Integer potionEffect,
    /* Determines the velocity of the projectile. */
    @JsonProperty("power") @Nullable Double power,
    /* During the specified time, in seconds, the projectile cannot be reflected by hitting it */
    @JsonProperty("reflect_immunity") @Nullable Double reflectImmunity,
    /* If true, this entity will be reflected back when hit. */
    @JsonProperty("reflect_on_hurt") @Nullable Boolean reflectOnHurt,
    /* If true, damage will be randomized based on damage and speed. */
    @JsonProperty("semi_random_diff_damage") @Nullable Boolean semiRandomDiffDamage,
    /* The sound that plays when the projectile is shot. */
    @JsonProperty("shoot_sound") @Nullable SoundEvent shootSound,
    /* If true, the projectile will be shot towards the target of the entity firing it. */
    @JsonProperty("shoot_target") @Nullable Boolean shootTarget,
    /* If true, the projectile will bounce upon hit. */
    @JsonProperty("should_bounce") @Nullable Boolean shouldBounce,
    /* If true, the projectile will be treated like a splash potion. */
    @JsonProperty("splash_potion") @Nullable Boolean splashPotion,
    /* Radius in blocks of the 'splash' effect. */
    @JsonProperty("splash_range") @Nullable Double splashRange,
    /* Determines if the projectile stops when the target is hurt. */
    @JsonProperty("stop_on_hurt") @Nullable Boolean stopOnHurt,
    /* The base accuracy. Accuracy is determined by the formula uncertaintyBase - difficultyLevel * uncertaintyMultiplier. */
    @JsonProperty("uncertainty_base") @Nullable Double uncertaintyBase,
    /* Determines how much difficulty affects accuracy. Accuracy is determined by the formula uncertaintyBase - difficultyLevel * uncertaintyMultiplier. */
    @JsonProperty("uncertainty_multiplier") @Nullable Double uncertaintyMultiplier
) {
    
        /* Defines the behaviors that may execute on a projectile's hit, including impact damage, impact effect, and stuck in ground. See more on these parameters below. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record OnHit(
            /* The target receives a mob effect. See the table below for all arrow_effect parameters. */
            @JsonProperty("arrow_effect") @Nullable ArrowEffect arrowEffect,
            /* Determines if the struck object is set on fire. */
            @JsonProperty("catch_fire") @Nullable Boolean catchFire,
            /* The event that is triggered on a hit. See the table below for all definition event parameters. */
            @JsonProperty("definition_event") @Nullable DefinitionEvent definitionEvent,
            /* If the target is on fire, then douse the fire. */
            @JsonProperty("douse_fire") @Nullable Boolean douseFire,
            /* An area of entities that is frozen to block on hits. Has shape of either sphere or cube, snap<i>to</i>block boolean ,and size decimal properties. */
            @JsonProperty("freeze_on_hit") @Nullable FreezeOnHit freezeOnHit,
            /* Grants XP on hit. Has minXP for minimum XP granted, maxXp for maximum, or simply flat xp properties. */
            @JsonProperty("grant_xp") @Nullable GrantXp grantXp,
            /* Determines if the owner of the entity is hurt on hit. Contains decimal owner_damage, knockback boolean, and ignite boolean. */
            @JsonProperty("hurt_owner") @Nullable HurtOwner hurtOwner,
            /* Determines if a fire may be started on a flammable target. */
            @JsonProperty("ignite") @Nullable Boolean ignite,
            /* Defines the damage that an entity may receive on being hit by this projectile. See the table below for all impact_damage parameters. */
            @JsonProperty("impact_damage") @Nullable ImpactDamage impactDamage,
            /* The target receives a mob effect. See the table below for all mob_effect parameters. */
            @JsonProperty("mob_effect") @Nullable MobEffect mobEffect,
            /* The amount of time a target will remain on fire. */
            @JsonProperty("on_fire_time") @Nullable Double onFireTime,
            /* The particles that spawn on hit. See the table below for all particle<i>on</i>hit parameters. */
            @JsonProperty("particle_on_hit") @Nullable ParticleOnHit particleOnHit,
            /* Defines the effect the arrow will apply to the entity it hits. */
            @JsonProperty("potion_effect") @Nullable Integer potionEffect,
            /* Removes the projectile. */
            @JsonProperty("remove_on_hit") @Nullable Map<String, Object> removeOnHit,
            /* Potion spawns an area of effect cloud. See the table below for all spawn<i>aoe</i>cloud parameters. */
            @JsonProperty("spawn_aoe_cloud") @Nullable SpawnAoeCloud spawnAoeCloud,
            /* Contains information on the chance of spawning an entity on hit. See parameters below. */
            @JsonProperty("spawn_chance") @Nullable SpawnChance spawnChance,
            /* Decides if the object sticks in ground and contains shake_time integer parameter to determine how long it will shake. */
            @JsonProperty("stick_in_ground") @Nullable net.easecation.bridge.core.dto.EmptyObject stickInGround,
            /* Determines if the owner is transported on hit. */
            @JsonProperty("teleport_owner") @Nullable Boolean teleportOwner,
            /* Creates a splash area for effects caused by a thrown potion. */
            @JsonProperty("thrown_potion_effect") @Nullable net.easecation.bridge.core.dto.EmptyObject thrownPotionEffect
        ) {
            
                /* The target receives a mob effect. See the table below for all arrow_effect parameters. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record ArrowEffect(
                    /* If true, the effect will be applied to blocking targets. */
                    @JsonProperty("apply_effect_to_blocking_targets") @Nullable Boolean applyEffectToBlockingTargets
                ) {
                }
            
                /* The event that is triggered on a hit. See the table below for all definition event parameters. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record DefinitionEvent(
                    /* The projectile that will be affected by this event. */
                    @JsonProperty("affect_projectile") @Nullable Boolean affectProjectile,
                    /* The shooter that will be affected by this event. */
                    @JsonProperty("affect_shooter") @Nullable Boolean affectShooter,
                    /* All entities in the splash area will be affected by this event. */
                    @JsonProperty("affect_splash_area") @Nullable Boolean affectSplashArea,
                    /* The target will be affected by this event. */
                    @JsonProperty("affect_target") @Nullable Boolean affectTarget,
                    /* The event triggered. Also has an option filters parameter to limit affected targets. */
                    @JsonProperty("event_trigger") @Nullable Event eventTrigger,
                    /* The splash area that will be affected. */
                    @JsonProperty("splash_area") @Nullable Double splashArea
                ) {
                }
            
                /* An area of entities that is frozen to block on hits. Has shape of either sphere or cube, snap<i>to</i>block boolean ,and size decimal properties. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record FreezeOnHit(
                    /* The shape of the area that is frozen. */
                    @JsonProperty("shape") @Nullable String shape,
                    /* If true, the area will snap to the nearest block. */
                    @JsonProperty("snap_to_block") @Nullable Boolean snapToBlock,
                    /* The size of the area that is frozen. */
                    @JsonProperty("size") @Nullable Double size
                ) {
                }
            
                /* Grants XP on hit. Has minXP for minimum XP granted, maxXp for maximum, or simply flat xp properties. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record GrantXp(
                    /* The minimum XP granted. */
                    @JsonProperty("minXP") @Nullable Double minxp,
                    /* The maximum XP granted. */
                    @JsonProperty("maxXP") @Nullable Double maxxp
                ) {
                }
            
                /* Determines if the owner of the entity is hurt on hit. Contains decimal owner_damage, knockback boolean, and ignite boolean. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record HurtOwner(
                    /* The amount of damage the owner will take. */
                    @JsonProperty("owner_damage") @Nullable Double ownerDamage,
                    /* If true, the owner will be knocked back. */
                    @JsonProperty("knockback") @Nullable Boolean knockback,
                    /* If true, the owner will be set on fire. */
                    @JsonProperty("ignite") @Nullable Boolean ignite
                ) {
                }
            
                /* Defines the damage that an entity may receive on being hit by this projectile. See the table below for all impact_damage parameters. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record ImpactDamage(
                    /* Determines if the struck object is set on fire. */
                    @JsonProperty("catch_fire") @Nullable Boolean catchFire,
                    /* Whether lightning can be channeled through hte weapon. */
                    @JsonProperty("channeling") @Nullable Boolean channeling,
                    /* The damage dealt on impact. */
                    @JsonProperty("damage") @Nullable Object damage,
                    /* Projectile is removed on hit. */
                    @JsonProperty("destroy_on_hit") @Nullable Boolean destroyOnHit,
                    /* If true, then the hit must cause damage to destroy the projectile. */
                    @JsonProperty("destroy_on_hit_requires_damage") @Nullable Boolean destroyOnHitRequiresDamage,
                    /* The identifier of an entity that can be hit. */
                    @JsonProperty("filter") @Nullable String filter,
                    /* If true, the projectile will knock back the entity it hits. */
                    @JsonProperty("knockback") @Nullable Boolean knockback,
                    /* Maximum critical damage. */
                    @JsonProperty("max_critical_damage") @Nullable Integer maxCriticalDamage,
                    /* Minimum critical damage. */
                    @JsonProperty("min_critical_damage") @Nullable Integer minCriticalDamage,
                    /* How much the base damage is multiplied. */
                    @JsonProperty("power_multiplier") @Nullable Double powerMultiplier,
                    /* If true, damage will be randomized based on damage and speed. */
                    @JsonProperty("semi_random_diff_damage") @Nullable Boolean semiRandomDiffDamage,
                    /* If true, then the hit must cause damage to update the last hurt property. */
                    @JsonProperty("set_last_hurt_requires_damage") @Nullable Boolean setLastHurtRequiresDamage,
                    /* If true, the projectile will bounce */
                    @JsonProperty("should_bounce") @Nullable Boolean shouldBounce
                ) {
                }
            
                /* The target receives a mob effect. See the table below for all mob_effect parameters. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record MobEffect(
                    /* If true, a mob will spawn that is not hostile, like the bat entity in Minecraft. */
                    @JsonProperty("ambient") @Nullable Boolean ambient,
                    /* The multiplier of the amplification of this effect. */
                    @JsonProperty("amplifier") @Nullable Integer amplifier,
                    /* The effect's duration. */
                    @JsonProperty("duration") @Nullable Object duration,
                    /* The effect's duration on easy mode. */
                    @JsonProperty("durationeasy") @Nullable Object durationeasy,
                    /* The effect's duration on hard mode. */
                    @JsonProperty("durationhard") @Nullable Object durationhard,
                    /* The effect's duration on normal mode. */
                    @JsonProperty("durationnormal") @Nullable Object durationnormal,
                    /* The identifier of the mob entity to affect. */
                    @JsonProperty("effect") @Nullable Effect effect,
                    /* Does the entity's look change. */
                    @JsonProperty("visible") @Nullable Boolean visible
                ) {
                }
            
                /* The particles that spawn on hit. See the table below for all particle<i>on</i>hit parameters. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record ParticleOnHit(
                    /* The number of particles to spawn. */
                    @JsonProperty("num_particles") @Nullable Double numParticles,
                    /* If true, spawns particles on an entity hit. */
                    @JsonProperty("on_entity_hit") @Nullable Boolean onEntityHit,
                    /* If true, spawns particles on any other hit. */
                    @JsonProperty("on_other_hit") @Nullable Boolean onOtherHit,
                    /* The id of the particle to spawn on hit. */
                    @JsonProperty("particle_type") @Nullable String particleType,
                    /* Maps an item name to an actor filter to determine what the name of the item used in the particle should be. */
                    @JsonProperty("particle_item_name") @Nullable Map<String, Filters> particleItemName
                ) {
                }
            
                /* Potion spawns an area of effect cloud. See the table below for all spawn<i>aoe</i>cloud parameters. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record SpawnAoeCloud(
                    /* Determines if the projectile shooter is affected. */
                    @JsonProperty("affect_owner") @Nullable Boolean affectOwner,
                    /* Particle color defined by three rgb values. */
                    @JsonProperty("color") @Nullable VectorOf3Items color,
                    /* How long the particle emits. */
                    @JsonProperty("duration") @Nullable Integer duration,
                    /* The particle emitter. */
                    @JsonProperty("particle") @Nullable String particle,
                    /* The id of the potion. */
                    @JsonProperty("potion") @Nullable Integer potion,
                    /* Defines the affected area. */
                    @JsonProperty("radius") @Nullable Double radius,
                    /* Defines the affected area when potion is used. */
                    @JsonProperty("radius_on_use") @Nullable Double radiusOnUse,
                    /* Delay before the potion can affect the area again. */
                    @JsonProperty("reapplication_delay") @Nullable Integer reapplicationDelay
                ) {
                }
            
                /* Contains information on the chance of spawning an entity on hit. See parameters below. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record SpawnChance(
                    /* The amount of new entities spawned. */
                    @JsonProperty("first_spawn_count") @Nullable Integer firstSpawnCount,
                    /* The chance that a spawn occurs when a projectile hits the entity. */
                    @JsonProperty("first_spawn_percent_chance") @Nullable Double firstSpawnPercentChance,
                    /* The chance that a first spawn occurs when a projectile hits the entity. */
                    @JsonProperty("first_spawn_chance") @Nullable Double firstSpawnChance,
                    /* The chance that a second spawn occurs when a projectile hits the entity. */
                    @JsonProperty("second_spawn_chance") @Nullable Double secondSpawnChance,
                    /* The amount of new entities spawned in teh second spawn. */
                    @JsonProperty("second_spawn_count") @Nullable Integer secondSpawnCount,
                    /* Determines if a baby spawns. */
                    @JsonProperty("spawn_baby") @Nullable Boolean spawnBaby,
                    /* The entity that will spawn. */
                    @JsonProperty("spawn_definition") @Nullable String spawnDefinition,
                    /* Triggered on the newly spawned entity with other set to the owning entity. */
                    @JsonProperty("on_spawn") @Nullable List<Trigger> onSpawn
                ) {
                }
        }
}
