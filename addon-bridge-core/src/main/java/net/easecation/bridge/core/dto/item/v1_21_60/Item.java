package net.easecation.bridge.core.dto.item.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Item(
    /* The description for this item */
    @JsonProperty("description") Description description,
    /* The components of this item. */
    @JsonProperty("components") @Nullable Components components
) {
    
        /* The description for this item */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The identifier for this item. The name must include a namespace and must not use the Minecraft namespace unless overriding a Vanilla item. */
            @JsonProperty("identifier") @Nullable String identifier,
            /* If this item is experimental, it will only be registered if the world is marked as experimental. */
            @JsonProperty("is_experimental") @Nullable Boolean isExperimental,
            /* The Creative Category that includes the specified item. */
            @JsonProperty("menu_category") @Nullable MenuCategory menuCategory
        ) {
            
                /* The Creative Category that includes the specified item. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record MenuCategory(
                    @JsonProperty("group") @Nullable String group,
                    @JsonProperty("category") @Nullable String category,
                    /* Determines whether or not this item can be used with commands. Commands can use items by default */
                    @JsonProperty("is_hidden_in_commands") @Nullable Boolean isHiddenInCommands
                ) {
                }
        }
    
        /* The components of this item. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Components(
            @JsonProperty("minecraft:allow_off_hand") @Nullable AllowOffHand minecraft_allowOffHand,
            @JsonProperty("minecraft:block_placer") @Nullable BlockPlacer minecraft_blockPlacer,
            @JsonProperty("minecraft:bundle_interaction") @Nullable BundleInteraction minecraft_bundleInteraction,
            @JsonProperty("minecraft:can_destroy_in_creative") @Nullable CanDestroyInCreative minecraft_canDestroyInCreative,
            @JsonProperty("minecraft:compostable") @Nullable Compostable minecraft_compostable,
            @JsonProperty("minecraft:cooldown") @Nullable Cooldown minecraft_cooldown,
            @JsonProperty("minecraft:custom_components") @Nullable CustomComponents minecraft_customComponents,
            @JsonProperty("minecraft:damage") @Nullable Damage minecraft_damage,
            @JsonProperty("minecraft:damage_absorption") @Nullable DamageAbsorption minecraft_damageAbsorption,
            @JsonProperty("minecraft:digger") @Nullable Digger minecraft_digger,
            @JsonProperty("minecraft:display_name") @Nullable DisplayName minecraft_displayName,
            @JsonProperty("minecraft:durability_sensor") @Nullable DurabilitySensor minecraft_durabilitySensor,
            @JsonProperty("minecraft:durability") @Nullable Durability minecraft_durability,
            @JsonProperty("minecraft:dyeable") @Nullable Dyeable minecraft_dyeable,
            @JsonProperty("minecraft:enchantable") @Nullable Enchantable minecraft_enchantable,
            @JsonProperty("minecraft:entity_placer") @Nullable EntityPlacer minecraft_entityPlacer,
            @JsonProperty("minecraft:food") @Nullable Food minecraft_food,
            @JsonProperty("minecraft:fuel") @Nullable Fuel minecraft_fuel,
            @JsonProperty("minecraft:glint") @Nullable Glint minecraft_glint,
            @JsonProperty("minecraft:hand_equipped") @Nullable HandEquipped minecraft_handEquipped,
            @JsonProperty("minecraft:icon") @Nullable Icon minecraft_icon,
            @JsonProperty("minecraft:liquid_clipped") @Nullable LiquidClipped minecraft_liquidClipped,
            @JsonProperty("minecraft:max_stack_size") @Nullable MaxStackSize minecraft_maxStackSize,
            @JsonProperty("minecraft:projectile") @Nullable Projectile minecraft_projectile,
            @JsonProperty("minecraft:record") @Nullable RecordField minecraft_record,
            @JsonProperty("minecraft:rarity") @Nullable Rarity minecraft_rarity,
            @JsonProperty("minecraft:repairable") @Nullable Repairable minecraft_repairable,
            @JsonProperty("minecraft:shooter") @Nullable Shooter minecraft_shooter,
            @JsonProperty("minecraft:should_despawn") @Nullable ShouldDespawn minecraft_shouldDespawn,
            @JsonProperty("minecraft:stacked_by_data") @Nullable StackedByData minecraft_stackedByData,
            @JsonProperty("minecraft:storage_item") @Nullable StorageItem minecraft_storageItem,
            @JsonProperty("minecraft:tags") @Nullable Tags minecraft_tags,
            @JsonProperty("minecraft:throwable") @Nullable Throwable minecraft_throwable,
            @JsonProperty("minecraft:use_animation") @Nullable UseAnimation minecraft_useAnimation,
            @JsonProperty("minecraft:use_modifiers") @Nullable UseModifiers minecraft_useModifiers,
            @JsonProperty("minecraft:wearable") @Nullable Wearable minecraft_wearable
        ) {
        }
}
