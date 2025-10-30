package net.easecation.bridge.core.dto.item.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Item(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* The components of this item. */
    @JsonProperty("components") @Nullable Components components,
    @JsonProperty("events") @Nullable Events events
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The identifier for this item. The name must include a namespace and must not use the Minecraft namespace unless overriding a Vanilla item. */
            @JsonProperty("identifier") @Nullable String identifier,
            /* The category for this item. Categories are used to control high level properties of how the item is integrated into the bedrock engine, such as whether it can be used in slash commands. */
            @JsonProperty("category") @Nullable String category,
            /* If this item is experimental, it will only be registered if the world is marked as experimental. */
            @JsonProperty("is_experimental") @Nullable Boolean isExperimental
        ) {
        }
    
        /* The components of this item. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Components(
            @JsonProperty("minecraft:armor") @Nullable Armor minecraft_armor,
            @JsonProperty("minecraft:block_placer") @Nullable BlockPlacer minecraft_blockPlacer,
            @JsonProperty("minecraft:cooldown") @Nullable Cooldown minecraft_cooldown,
            @JsonProperty("minecraft:digger") @Nullable Digger minecraft_digger,
            @JsonProperty("minecraft:display_name") @Nullable DisplayName minecraft_displayName,
            @JsonProperty("minecraft:durability") @Nullable Durability minecraft_durability,
            @JsonProperty("minecraft:dye_powder") @Nullable DyePowder minecraft_dyePowder,
            @JsonProperty("minecraft:entity_placer") @Nullable EntityPlacer minecraft_entityPlacer,
            @JsonProperty("minecraft:food") @Nullable Food minecraft_food,
            @JsonProperty("minecraft:fuel") @Nullable Fuel minecraft_fuel,
            @JsonProperty("minecraft:hand_equipped") @Nullable Boolean minecraft_handEquipped,
            @JsonProperty("minecraft:icon") @Nullable Icon minecraft_icon,
            @JsonProperty("minecraft:knockback_resistance") @Nullable KnockbackResistance minecraft_knockbackResistance,
            @JsonProperty("minecraft:max_damage") @Nullable Integer minecraft_maxDamage,
            @JsonProperty("minecraft:max_stack_size") @Nullable Integer minecraft_maxStackSize,
            @JsonProperty("minecraft:on_use_on") @Nullable OnUseOn minecraft_onUseOn,
            @JsonProperty("minecraft:on_use") @Nullable OnUse minecraft_onUse,
            @JsonProperty("minecraft:projectile") @Nullable Projectile minecraft_projectile,
            @JsonProperty("minecraft:render_offsets") @Nullable RenderOffsets minecraft_renderOffsets,
            @JsonProperty("minecraft:repairable") @Nullable Repairable minecraft_repairable,
            @JsonProperty("minecraft:shooter") @Nullable Shooter minecraft_shooter,
            @JsonProperty("minecraft:stacked_by_data") @Nullable KnockbackResistance minecraft_stackedByData,
            @JsonProperty("minecraft:throwable") @Nullable Throwable minecraft_throwable,
            @JsonProperty("minecraft:use_duration") @Nullable Integer minecraft_useDuration,
            @JsonProperty("minecraft:weapon") @Nullable Weapon minecraft_weapon,
            @JsonProperty("minecraft:wearable") @Nullable Wearable minecraft_wearable
        ) {
        }
}
