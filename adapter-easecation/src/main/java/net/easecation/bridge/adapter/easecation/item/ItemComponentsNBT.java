package net.easecation.bridge.adapter.easecation.item;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import net.easecation.bridge.core.ItemDef;

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
        Map<String, Object> components = itemDef.components();
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
        Object iconObj = components.get("minecraft:icon");
        if (iconObj != null) {
            CompoundTag iconTag = convertIcon(iconObj, identifier);
            tag.putCompound("minecraft:icon", iconTag);
            // Also add to item_properties for legacy compatibility
            itemProperties.putCompound("minecraft:icon", iconTag);
        }

        // Allow Off Hand
        Object allowOffHandObj = components.get("minecraft:allow_off_hand");
        if (allowOffHandObj instanceof Boolean) {
            itemProperties.putBoolean("allow_off_hand", (Boolean) allowOffHandObj);
        }

        // Max Stack Size
        Object maxStackSizeObj = components.get("minecraft:max_stack_size");
        if (maxStackSizeObj != null) {
            int stackSize = extractMaxStackSize(maxStackSizeObj);
            tag.putCompound("minecraft:max_stack_size",
                new CompoundTag().putByte("value", (byte) stackSize));
            itemProperties.putInt("max_stack_size", stackSize);
        }

        // Add item_properties to main tag if not empty
        if (!itemProperties.isEmpty()) {
            tag.putCompound("item_properties", itemProperties);
        }

        // === Core Components ===

        // Display Name
        Object displayNameObj = components.get("minecraft:display_name");
        if (displayNameObj != null) {
            String displayName = extractDisplayName(displayNameObj);
            if (displayName != null) {
                tag.putCompound("minecraft:display_name",
                    new CompoundTag().putString("value", displayName));
            }
        }

        // Durability
        Object durabilityObj = components.get("minecraft:durability");
        if (durabilityObj instanceof Map) {
            tag.putCompound("minecraft:durability", convertDurability(durabilityObj));
        }

        // Food
        Object foodObj = components.get("minecraft:food");
        if (foodObj instanceof Map) {
            tag.putCompound("minecraft:food", convertFood(foodObj));
        }

        // Wearable (Armor)
        Object wearableObj = components.get("minecraft:wearable");
        if (wearableObj instanceof Map) {
            tag.putCompound("minecraft:wearable", convertWearable(wearableObj));
        }

        // Damage
        Object damageObj = components.get("minecraft:damage");
        if (damageObj instanceof Number) {
            tag.putCompound("minecraft:damage",
                new CompoundTag().putInt("value", ((Number) damageObj).intValue()));
        }

        // Enchantable
        Object enchantableObj = components.get("minecraft:enchantable");
        if (enchantableObj instanceof Map) {
            tag.putCompound("minecraft:enchantable", convertEnchantable(enchantableObj));
        }

        // Repairable
        Object repairableObj = components.get("minecraft:repairable");
        if (repairableObj instanceof Map) {
            tag.putCompound("minecraft:repairable", convertRepairable(repairableObj));
        }

        // Cooldown
        Object cooldownObj = components.get("minecraft:cooldown");
        if (cooldownObj instanceof Map) {
            tag.putCompound("minecraft:cooldown", convertCooldown(cooldownObj));
        }

        // Throwable
        Object throwableObj = components.get("minecraft:throwable");
        if (throwableObj instanceof Map) {
            tag.putCompound("minecraft:throwable", convertThrowable(throwableObj));
        }

        // Projectile
        Object projectileObj = components.get("minecraft:projectile");
        if (projectileObj instanceof Map) {
            tag.putCompound("minecraft:projectile", convertProjectile(projectileObj));
        }

        // Fuel
        Object fuelObj = components.get("minecraft:fuel");
        if (fuelObj instanceof Map) {
            tag.putCompound("minecraft:fuel", convertFuel(fuelObj));
        }

        // Block Placer
        Object blockPlacerObj = components.get("minecraft:block_placer");
        if (blockPlacerObj instanceof Map) {
            tag.putCompound("minecraft:block_placer", convertBlockPlacer(blockPlacerObj));
        }

        // Entity Placer
        Object entityPlacerObj = components.get("minecraft:entity_placer");
        if (entityPlacerObj instanceof Map) {
            tag.putCompound("minecraft:entity_placer", convertEntityPlacer(entityPlacerObj));
        }

        // Digger
        Object diggerObj = components.get("minecraft:digger");
        if (diggerObj instanceof Map) {
            tag.putCompound("minecraft:digger", convertDigger(diggerObj));
        }

        // Use Animation
        Object useAnimationObj = components.get("minecraft:use_animation");
        if (useAnimationObj instanceof String) {
            tag.putCompound("minecraft:use_animation",
                new CompoundTag().putString("value", (String) useAnimationObj));
        }

        // Hand Equipped
        Object handEquippedObj = components.get("minecraft:hand_equipped");
        if (handEquippedObj instanceof Boolean) {
            tag.putCompound("minecraft:hand_equipped",
                new CompoundTag().putBoolean("value", (Boolean) handEquippedObj));
        }

        // Stacked By Data
        Object stackedByDataObj = components.get("minecraft:stacked_by_data");
        if (stackedByDataObj instanceof Boolean) {
            tag.putCompound("minecraft:stacked_by_data",
                new CompoundTag().putBoolean("value", (Boolean) stackedByDataObj));
        }

        // Can Destroy In Creative
        Object canDestroyInCreativeObj = components.get("minecraft:can_destroy_in_creative");
        if (canDestroyInCreativeObj instanceof Boolean) {
            tag.putCompound("minecraft:can_destroy_in_creative",
                new CompoundTag().putBoolean("value", (Boolean) canDestroyInCreativeObj));
        }

        // Liquid Clipped
        Object liquidClippedObj = components.get("minecraft:liquid_clipped");
        if (liquidClippedObj instanceof Boolean) {
            tag.putCompound("minecraft:liquid_clipped",
                new CompoundTag().putBoolean("value", (Boolean) liquidClippedObj));
        }

        // Should Despawn
        Object shouldDespawnObj = components.get("minecraft:should_despawn");
        if (shouldDespawnObj instanceof Boolean) {
            tag.putCompound("minecraft:should_despawn",
                new CompoundTag().putBoolean("value", (Boolean) shouldDespawnObj));
        }

        // Glint
        Object glintObj = components.get("minecraft:glint");
        if (glintObj instanceof Boolean) {
            tag.putCompound("minecraft:glint",
                new CompoundTag().putBoolean("value", (Boolean) glintObj));
        }

        return tag;
    }

    // === Helper Methods for Component Extraction ===

    private static int extractMaxStackSize(Object maxStackSizeObj) {
        if (maxStackSizeObj instanceof Number) {
            // Direct number value (Variant0)
            return ((Number) maxStackSizeObj).intValue();
        } else if (maxStackSizeObj instanceof Map) {
            // Object with value field (Variant1)
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) maxStackSizeObj;
            Object value = map.get("value");
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
        }
        return 64;
    }

    private static String extractDisplayName(Object displayNameObj) {
        if (displayNameObj instanceof String) {
            return (String) displayNameObj;
        } else if (displayNameObj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) displayNameObj;
            Object value = map.get("value");
            if (value instanceof String) {
                return (String) value;
            }
        }
        return null;
    }

    // === Component Converters ===

    private static CompoundTag convertIcon(Object iconObj, String identifier) {
        CompoundTag tag = new CompoundTag();
        String textureName = null;

        if (iconObj instanceof String) {
            // Simple string texture name (Variant0)
            textureName = (String) iconObj;
        } else if (iconObj instanceof Map) {
            // Object with textures map (Variant1)
            @SuppressWarnings("unchecked")
            Map<String, Object> iconMap = (Map<String, Object>) iconObj;
            Object texturesObj = iconMap.get("textures");

            if (texturesObj instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, String> texturesMap = (Map<String, String>) texturesObj;
                // Use the default texture or first available
                String texture = texturesMap.get("default");
                if (texture == null && !texturesMap.isEmpty()) {
                    texture = texturesMap.values().iterator().next();
                }
                textureName = texture;
            }
        }

        // Fallback: use identifier as texture name
        if (textureName == null || textureName.isEmpty()) {
            textureName = identifier.replace(":", "_");
        }

        // Set both texture string and textures object to satisfy EaseCation requirements
        // Reference: CustomItemSample.java lines 44-49
        tag.putString("texture", textureName);
        tag.putCompound("textures", new CompoundTag()
            .putString("default", textureName)
        );

        return tag;
    }

    private static CompoundTag convertDurability(Object durabilityObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> durabilityMap = (Map<String, Object>) durabilityObj;
        CompoundTag tag = new CompoundTag();

        Object maxDurability = durabilityMap.get("max_durability");
        if (maxDurability instanceof Number) {
            tag.putInt("max_durability", ((Number) maxDurability).intValue());
        }

        Object damageChanceObj = durabilityMap.get("damage_chance");
        if (damageChanceObj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> damageChanceMap = (Map<String, Object>) damageChanceObj;
            CompoundTag damageChanceTag = new CompoundTag();

            Object min = damageChanceMap.get("min");
            if (min instanceof Number) {
                damageChanceTag.putInt("min", ((Number) min).intValue());
            }

            Object max = damageChanceMap.get("max");
            if (max instanceof Number) {
                damageChanceTag.putInt("max", ((Number) max).intValue());
            }

            tag.putCompound("damage_chance", damageChanceTag);
        }

        return tag;
    }

    private static CompoundTag convertFood(Object foodObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> foodMap = (Map<String, Object>) foodObj;
        CompoundTag tag = new CompoundTag();

        Object canAlwaysEat = foodMap.get("can_always_eat");
        if (canAlwaysEat instanceof Boolean) {
            tag.putBoolean("can_always_eat", (Boolean) canAlwaysEat);
        }

        Object nutrition = foodMap.get("nutrition");
        if (nutrition instanceof Number) {
            tag.putInt("nutrition", ((Number) nutrition).intValue());
        }

        Object saturationModifier = foodMap.get("saturation_modifier");
        if (saturationModifier instanceof Number) {
            tag.putFloat("saturation_modifier", ((Number) saturationModifier).floatValue());
        }

        Object usingConvertsTo = foodMap.get("using_converts_to");
        if (usingConvertsTo instanceof String) {
            tag.putString("using_converts_to", (String) usingConvertsTo);
        }

        return tag;
    }

    private static CompoundTag convertWearable(Object wearableObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> wearableMap = (Map<String, Object>) wearableObj;
        CompoundTag tag = new CompoundTag();

        Object slot = wearableMap.get("slot");
        if (slot instanceof String) {
            tag.putString("slot", (String) slot);
        }

        Object protection = wearableMap.get("protection");
        if (protection instanceof Number) {
            tag.putInt("protection", ((Number) protection).intValue());
        }

        Object dispensable = wearableMap.get("dispensable");
        if (dispensable instanceof Boolean) {
            tag.putBoolean("dispensable", (Boolean) dispensable);
        }

        return tag;
    }

    private static CompoundTag convertEnchantable(Object enchantableObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> enchantableMap = (Map<String, Object>) enchantableObj;
        CompoundTag tag = new CompoundTag();

        Object slot = enchantableMap.get("slot");
        if (slot instanceof String) {
            tag.putString("slot", (String) slot);
        }

        Object value = enchantableMap.get("value");
        if (value instanceof Number) {
            tag.putInt("value", ((Number) value).intValue());
        }

        return tag;
    }

    private static CompoundTag convertRepairable(Object repairableObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> repairableMap = (Map<String, Object>) repairableObj;
        CompoundTag tag = new CompoundTag();

        Object repairItems = repairableMap.get("repair_items");
        if (repairItems instanceof List) {
            // TODO: Convert list of repair items if needed
        }

        return tag;
    }

    private static CompoundTag convertCooldown(Object cooldownObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> cooldownMap = (Map<String, Object>) cooldownObj;
        CompoundTag tag = new CompoundTag();

        Object category = cooldownMap.get("category");
        if (category instanceof String) {
            tag.putString("category", (String) category);
        }

        Object duration = cooldownMap.get("duration");
        if (duration instanceof Number) {
            tag.putFloat("duration", ((Number) duration).floatValue());
        }

        return tag;
    }

    private static CompoundTag convertThrowable(Object throwableObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> throwableMap = (Map<String, Object>) throwableObj;
        CompoundTag tag = new CompoundTag();

        Object doSwingAnimation = throwableMap.get("do_swing_animation");
        if (doSwingAnimation instanceof Boolean) {
            tag.putBoolean("do_swing_animation", (Boolean) doSwingAnimation);
        }

        Object launchPowerScale = throwableMap.get("launch_power_scale");
        if (launchPowerScale instanceof Number) {
            tag.putFloat("launch_power_scale", ((Number) launchPowerScale).floatValue());
        }

        Object maxDrawDuration = throwableMap.get("max_draw_duration");
        if (maxDrawDuration instanceof Number) {
            tag.putFloat("max_draw_duration", ((Number) maxDrawDuration).floatValue());
        }

        Object maxLaunchPower = throwableMap.get("max_launch_power");
        if (maxLaunchPower instanceof Number) {
            tag.putFloat("max_launch_power", ((Number) maxLaunchPower).floatValue());
        }

        Object minDrawDuration = throwableMap.get("min_draw_duration");
        if (minDrawDuration instanceof Number) {
            tag.putFloat("min_draw_duration", ((Number) minDrawDuration).floatValue());
        }

        Object scalePowerByDrawDuration = throwableMap.get("scale_power_by_draw_duration");
        if (scalePowerByDrawDuration instanceof Boolean) {
            tag.putBoolean("scale_power_by_draw_duration", (Boolean) scalePowerByDrawDuration);
        }

        return tag;
    }

    private static CompoundTag convertProjectile(Object projectileObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> projectileMap = (Map<String, Object>) projectileObj;
        CompoundTag tag = new CompoundTag();

        Object projectileEntity = projectileMap.get("projectile_entity");
        if (projectileEntity instanceof String) {
            tag.putString("projectile_entity", (String) projectileEntity);
        }

        Object minimumCriticalPower = projectileMap.get("minimum_critical_power");
        if (minimumCriticalPower instanceof Number) {
            tag.putFloat("minimum_critical_power", ((Number) minimumCriticalPower).floatValue());
        }

        return tag;
    }

    private static CompoundTag convertFuel(Object fuelObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> fuelMap = (Map<String, Object>) fuelObj;
        CompoundTag tag = new CompoundTag();

        Object duration = fuelMap.get("duration");
        if (duration instanceof Number) {
            tag.putFloat("duration", ((Number) duration).floatValue());
        }

        return tag;
    }

    private static CompoundTag convertBlockPlacer(Object blockPlacerObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> blockPlacerMap = (Map<String, Object>) blockPlacerObj;
        CompoundTag tag = new CompoundTag();

        Object block = blockPlacerMap.get("block");
        if (block instanceof String) {
            tag.putString("block", (String) block);
        }

        Object useOn = blockPlacerMap.get("use_on");
        if (useOn instanceof List) {
            // TODO: Convert list of blocks if needed
        }

        return tag;
    }

    private static CompoundTag convertEntityPlacer(Object entityPlacerObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> entityPlacerMap = (Map<String, Object>) entityPlacerObj;
        CompoundTag tag = new CompoundTag();

        Object entity = entityPlacerMap.get("entity");
        if (entity instanceof String) {
            tag.putString("entity", (String) entity);
        }

        Object useOn = entityPlacerMap.get("use_on");
        if (useOn instanceof List) {
            // TODO: Convert list of blocks if needed
        }

        Object dispenseOn = entityPlacerMap.get("dispense_on");
        if (dispenseOn instanceof List) {
            // TODO: Convert list of blocks if needed
        }

        return tag;
    }

    private static CompoundTag convertDigger(Object diggerObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> diggerMap = (Map<String, Object>) diggerObj;
        CompoundTag tag = new CompoundTag();

        Object useEfficiency = diggerMap.get("use_efficiency");
        if (useEfficiency instanceof Boolean) {
            tag.putBoolean("use_efficiency", (Boolean) useEfficiency);
        }

        Object destroySpeeds = diggerMap.get("destroy_speeds");
        if (destroySpeeds instanceof List) {
            // TODO: Convert list of destroy speeds if needed
        }

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
