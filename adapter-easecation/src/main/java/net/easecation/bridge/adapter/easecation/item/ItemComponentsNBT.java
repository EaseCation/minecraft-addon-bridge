package net.easecation.bridge.adapter.easecation.item;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import net.easecation.bridge.core.ItemDef;
import net.easecation.bridge.core.dto.item.v1_21_60.*;

import java.util.List;
import java.util.Map;

/**
 * Utility class for converting item components to NBT format.
 * Converts Map-based component data from ItemDef to Nukkit NBT tags.
 */
public class ItemComponentsNBT {

    /**
     * Convert item components to CompoundTag for item registration.
     *
     * @param itemDef The complete ItemDef containing components and menu_category
     * @param identifier The item identifier (used for icon default)
     * @return CompoundTag containing all component NBT data
     */
    public static CompoundTag toNBT(ItemDef itemDef, String identifier) {
        var components = itemDef.componentComponents();
        if (components == null) {
            return new CompoundTag();
        }

        CompoundTag tag = new CompoundTag();

        // === item_properties (legacy compatibility) ===
        CompoundTag itemProperties = new CompoundTag();

        // Creative mode category (required for item to appear in creative inventory)
        if (itemDef.menuCategory() != null && itemDef.menuCategory().category() != null) {
            int categoryId = mapCategoryToId(itemDef.menuCategory().category());
            itemProperties.putInt("creative_category", categoryId);

            if (itemDef.menuCategory().group() != null && !itemDef.menuCategory().group().isEmpty()) {
                itemProperties.putString("creative_group", itemDef.menuCategory().group());
            }
        }

        // Icon
        if (components.minecraft_icon() != null) {
            CompoundTag iconTag = convertIcon(components.minecraft_icon(), identifier);
            tag.putCompound("minecraft:icon", iconTag);
            // Also add to item_properties for legacy compatibility
            itemProperties.putCompound("minecraft:icon", iconTag);
        }

        // Allow Off Hand
        if (components.minecraft_allowOffHand() != null) {
            Boolean value = extractBooleanValue(components.minecraft_allowOffHand());
            if (value != null) {
                itemProperties.putBoolean("allow_off_hand", value);
            }
        }

        // Max Stack Size
        if (components.minecraft_maxStackSize() != null) {
            Double value = extractDoubleValue(components.minecraft_maxStackSize());
            if (value != null) {
                int stackSize = value.intValue();
                tag.putCompound("minecraft:max_stack_size",
                    new CompoundTag().putByte("value", (byte) stackSize));
                itemProperties.putInt("max_stack_size", stackSize);
            }
        }

        // Add item_properties to main tag if not empty
        if (!itemProperties.isEmpty()) {
            tag.putCompound("item_properties", itemProperties);
        }

        // === Core Components ===

        // Display Name
        if (components.minecraft_displayName() != null && components.minecraft_displayName().value() != null) {
            tag.putCompound("minecraft:display_name",
                new CompoundTag().putString("value", components.minecraft_displayName().value()));
        }

        // Durability
        if (components.minecraft_durability() != null) {
            tag.putCompound("minecraft:durability", convertDurability(components.minecraft_durability()));
        }

        // Food
        if (components.minecraft_food() != null) {
            tag.putCompound("minecraft:food", convertFood(components.minecraft_food()));
        }

        // Wearable (Armor)
        if (components.minecraft_wearable() != null) {
            tag.putCompound("minecraft:wearable", convertWearable(components.minecraft_wearable()));
        }

        // Damage
        if (components.minecraft_damage() != null) {
            Integer value = extractIntValue(components.minecraft_damage());
            if (value != null) {
                tag.putCompound("minecraft:damage",
                    new CompoundTag().putInt("value", value));
            }
        }

        // Enchantable
        if (components.minecraft_enchantable() != null) {
            tag.putCompound("minecraft:enchantable", convertEnchantable(components.minecraft_enchantable()));
        }

        // Repairable
        if (components.minecraft_repairable() != null) {
            tag.putCompound("minecraft:repairable", convertRepairable(components.minecraft_repairable()));
        }

        // Cooldown
        if (components.minecraft_cooldown() != null) {
            tag.putCompound("minecraft:cooldown", convertCooldown(components.minecraft_cooldown()));
        }

        // Throwable (now Shooter in v1_21_60)
        if (components.minecraft_shooter() != null) {
            tag.putCompound("minecraft:throwable", convertShooter(components.minecraft_shooter()));
        }

        // Projectile
        if (components.minecraft_projectile() != null) {
            tag.putCompound("minecraft:projectile", convertProjectile(components.minecraft_projectile()));
        }

        // Fuel
        if (components.minecraft_fuel() != null) {
            tag.putCompound("minecraft:fuel", convertFuel(components.minecraft_fuel()));
        }

        // Block Placer
        if (components.minecraft_blockPlacer() != null) {
            tag.putCompound("minecraft:block_placer", convertBlockPlacer(components.minecraft_blockPlacer()));
        }

        // Entity Placer
        if (components.minecraft_entityPlacer() != null) {
            tag.putCompound("minecraft:entity_placer", convertEntityPlacer(components.minecraft_entityPlacer()));
        }

        // Digger
        if (components.minecraft_digger() != null) {
            tag.putCompound("minecraft:digger", convertDigger(components.minecraft_digger()));
        }

        // Use Animation (not in v1_21_60 schema)
        // Skipped

        // Hand Equipped
        if (components.minecraft_handEquipped() != null) {
            Boolean value = extractBooleanValue(components.minecraft_handEquipped());
            if (value != null) {
                tag.putCompound("minecraft:hand_equipped",
                    new CompoundTag().putBoolean("value", value));
            }
        }

        // Stacked By Data
        if (components.minecraft_stackedByData() != null) {
            Boolean value = extractBooleanValue(components.minecraft_stackedByData());
            if (value != null) {
                tag.putCompound("minecraft:stacked_by_data",
                    new CompoundTag().putBoolean("value", value));
            }
        }

        // Can Destroy In Creative
        if (components.minecraft_canDestroyInCreative() != null) {
            Boolean value = extractBooleanValue(components.minecraft_canDestroyInCreative());
            if (value != null) {
                tag.putCompound("minecraft:can_destroy_in_creative",
                    new CompoundTag().putBoolean("value", value));
            }
        }

        // Liquid Clipped
        if (components.minecraft_liquidClipped() != null) {
            Boolean value = extractBooleanValue(components.minecraft_liquidClipped());
            if (value != null) {
                tag.putCompound("minecraft:liquid_clipped",
                    new CompoundTag().putBoolean("value", value));
            }
        }

        // Should Despawn
        if (components.minecraft_shouldDespawn() != null) {
            Boolean value = extractBooleanValue(components.minecraft_shouldDespawn());
            if (value != null) {
                tag.putCompound("minecraft:should_despawn",
                    new CompoundTag().putBoolean("value", value));
            }
        }

        // Glint
        if (components.minecraft_glint() != null) {
            Boolean value = extractBooleanValue(components.minecraft_glint());
            if (value != null) {
                tag.putCompound("minecraft:glint",
                    new CompoundTag().putBoolean("value", value));
            }
        }

        return tag;
    }

    // === Helper Methods for Sealed Interface Extraction ===

    private static Boolean extractBooleanValue(Object component) {
        // Handle sealed interfaces with Variant0 that have a Boolean value
        try {
            var method = component.getClass().getMethod("value");
            Object result = method.invoke(component);
            if (result instanceof Boolean) {
                return (Boolean) result;
            }
        } catch (Exception e) {
            // Ignore and return null
        }
        return null;
    }

    private static Integer extractIntValue(Object component) {
        // Handle sealed interfaces with Variant0 that have an Integer value
        try {
            var method = component.getClass().getMethod("value");
            Object result = method.invoke(component);
            if (result instanceof Integer) {
                return (Integer) result;
            }
        } catch (Exception e) {
            // Ignore and return null
        }
        return null;
    }

    private static Double extractDoubleValue(Object component) {
        // Handle sealed interfaces with Variant0/Variant1 that have a Double value
        try {
            var method = component.getClass().getMethod("value");
            Object result = method.invoke(component);
            if (result instanceof Double) {
                return (Double) result;
            }
        } catch (Exception e) {
            // Ignore and return null
        }
        return null;
    }

    private static String extractStringValue(Object component) {
        // Handle sealed interfaces with Variant0 that have a String value
        try {
            var method = component.getClass().getMethod("value");
            Object result = method.invoke(component);
            if (result instanceof String) {
                return (String) result;
            }
        } catch (Exception e) {
            // Ignore and return null
        }
        return null;
    }

    // === Component Converters ===

    private static CompoundTag convertIcon(Icon icon, String identifier) {
        CompoundTag tag = new CompoundTag();
        String textureName = extractStringValue(icon);

        // Fallback: use identifier as texture name
        if (textureName == null || textureName.isEmpty()) {
            textureName = identifier.replace(":", "_");
        }

        // Set both texture string and textures object to satisfy EaseCation requirements
        tag.putString("texture", textureName);
        tag.putCompound("textures", new CompoundTag()
            .putString("default", textureName)
        );

        return tag;
    }

    private static CompoundTag convertDurability(Durability durability) {
        CompoundTag tag = new CompoundTag();

        if (durability.maxDurability() != null) {
            tag.putInt("max_durability", durability.maxDurability());
        }

        if (durability.damageChance() != null) {
            CompoundTag damageChanceTag = new CompoundTag();
            if (durability.damageChance().min() != null) {
                damageChanceTag.putInt("min", durability.damageChance().min());
            }
            if (durability.damageChance().max() != null) {
                damageChanceTag.putInt("max", durability.damageChance().max());
            }
            tag.putCompound("damage_chance", damageChanceTag);
        }

        return tag;
    }

    private static CompoundTag convertFood(Food food) {
        CompoundTag tag = new CompoundTag();

        if (food.canAlwaysEat() != null) {
            tag.putBoolean("can_always_eat", food.canAlwaysEat());
        }

        if (food.nutrition() != null) {
            tag.putInt("nutrition", food.nutrition().intValue());
        }

        if (food.saturationModifier() != null) {
            tag.putFloat("saturation_modifier", food.saturationModifier().floatValue());
        }

        if (food.usingConvertsTo() != null) {
            tag.putString("using_converts_to", food.usingConvertsTo());
        }

        return tag;
    }

    private static CompoundTag convertWearable(Wearable wearable) {
        CompoundTag tag = new CompoundTag();

        if (wearable.slot() != null) {
            tag.putString("slot", wearable.slot());
        }

        if (wearable.protection() != null) {
            tag.putInt("protection", wearable.protection());
        }

        if (wearable.dispensable() != null) {
            tag.putBoolean("dispensable", wearable.dispensable());
        }

        return tag;
    }

    private static CompoundTag convertEnchantable(Enchantable enchantable) {
        CompoundTag tag = new CompoundTag();

        if (enchantable.slot() != null) {
            tag.putString("slot", enchantable.slot());
        }

        if (enchantable.value() != null) {
            tag.putInt("value", enchantable.value().intValue());
        }

        return tag;
    }

    private static CompoundTag convertRepairable(Repairable repairable) {
        CompoundTag tag = new CompoundTag();
        // TODO: Convert repair_items if needed
        return tag;
    }

    private static CompoundTag convertCooldown(Cooldown cooldown) {
        CompoundTag tag = new CompoundTag();

        if (cooldown.category() != null) {
            tag.putString("category", cooldown.category());
        }

        if (cooldown.duration() != null) {
            tag.putFloat("duration", cooldown.duration().floatValue());
        }

        return tag;
    }

    private static CompoundTag convertShooter(Shooter shooter) {
        CompoundTag tag = new CompoundTag();

        // Map Shooter fields to Throwable format
        if (shooter.maxDrawDuration() != null) {
            tag.putFloat("max_draw_duration", shooter.maxDrawDuration().floatValue());
        }

        if (shooter.scalePowerByDrawDuration() != null) {
            tag.putBoolean("scale_power_by_draw_duration", shooter.scalePowerByDrawDuration());
        }

        return tag;
    }

    private static CompoundTag convertProjectile(Projectile projectile) {
        CompoundTag tag = new CompoundTag();

        if (projectile.projectileEntity() != null) {
            tag.putString("projectile_entity", projectile.projectileEntity());
        }

        if (projectile.minimumCriticalPower() != null) {
            tag.putFloat("minimum_critical_power", projectile.minimumCriticalPower().floatValue());
        }

        return tag;
    }

    private static CompoundTag convertFuel(Fuel fuel) {
        CompoundTag tag = new CompoundTag();

        if (fuel.duration() != null) {
            tag.putFloat("duration", fuel.duration().floatValue());
        }

        return tag;
    }

    private static CompoundTag convertBlockPlacer(BlockPlacer blockPlacer) {
        CompoundTag tag = new CompoundTag();

        if (blockPlacer.block() != null) {
            tag.putString("block", blockPlacer.block());
        }

        // TODO: Convert use_on if needed

        return tag;
    }

    private static CompoundTag convertEntityPlacer(EntityPlacer entityPlacer) {
        CompoundTag tag = new CompoundTag();

        if (entityPlacer.entity() != null) {
            tag.putString("entity", entityPlacer.entity());
        }

        // TODO: Convert use_on and dispense_on if needed

        return tag;
    }

    private static CompoundTag convertDigger(Digger digger) {
        CompoundTag tag = new CompoundTag();

        if (digger.useEfficiency() != null) {
            tag.putBoolean("use_efficiency", digger.useEfficiency());
        }

        // TODO: Convert destroy_speeds if needed

        return tag;
    }

    /**
     * Map category string to creative mode category ID.
     * Based on CustomItemSample.java reference implementation.
     *
     * @param category Category string (CONSTRUCTION, EQUIPMENT, ITEMS, NATURE, NONE)
     * @return Category ID for NBT
     */
    private static int mapCategoryToId(String category) {
        if (category == null) {
            return 4; // Default to ITEMS
        }

        return switch (category.toUpperCase()) {
            case "CONSTRUCTION" -> 1;
            case "NATURE" -> 2;
            case "EQUIPMENT" -> 3;
            case "ITEMS" -> 4;
            case "NONE" -> 0;
            default -> 4;  // Default to ITEMS
        };
    }
}
