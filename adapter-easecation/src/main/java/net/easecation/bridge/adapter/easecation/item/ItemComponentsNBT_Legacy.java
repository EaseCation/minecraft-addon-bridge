package net.easecation.bridge.adapter.easecation.item;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import net.easecation.bridge.core.ItemDef;

/**
 * Utility class for converting Legacy mode (v1.10) item components to NBT format.
 *
 * <p>Legacy mode registration differs from Component-based mode:
 * <ul>
 *   <li>Legacy simple item: No NBT (returns null)</li>
 *   <li>Legacy food item: Only minecraft:food and minecraft:use_duration components</li>
 *   <li>Component-based item: Full item_properties with all components</li>
 * </ul>
 */
public class ItemComponentsNBT_Legacy {

    /**
     * Build NBT for Legacy mode (v1.10) items.
     *
     * @param itemDef The ItemDef containing Legacy components
     * @return CompoundTag for food items, null for simple items
     */
    public static CompoundTag toNBT(ItemDef itemDef) {
        var components = itemDef.legacyComponents();

        if (components == null) {
            return null; // No NBT for minimal items
        }

        // Check if this is a food item
        boolean hasFood = components.getFood() != null;

        if (!hasFood) {
            // Legacy simple item - no NBT needed
            return null;
        }

        // Legacy food item - build NBT with food component
        CompoundTag nbt = new CompoundTag();

        // minecraft:food component
        var food = components.getFood();
        CompoundTag foodTag = new CompoundTag();

        if (food.canAlwaysEat() != null) {
            foodTag.putBoolean("can_always_eat", food.canAlwaysEat());
        }

        if (food.nutrition() != null) {
            foodTag.putInt("nutrition", food.nutrition());
        }

        if (food.saturationModifier() != null) {
            // Convert string to float
            float saturation = parseSaturationModifier(food.saturationModifier());
            foodTag.putFloat("saturation_modifier", saturation);
        }

        if (food.usingConvertsTo() != null) {
            foodTag.putString("using_converts_to", food.usingConvertsTo());
        }

        // cooldown_type and cooldown_time
        if (food.cooldownType() != null) {
            foodTag.putString("cooldown_type", food.cooldownType());
        }

        if (food.cooldownTime() != null) {
            foodTag.putInt("cooldown_time", food.cooldownTime());
        }

        // on_use_action (String type)
        if (food.onUseAction() != null) {
            foodTag.putString("on_use_action", food.onUseAction());
        }

        // on_use_range (List<Integer> type)
        if (food.onUseRange() != null && food.onUseRange().size() == 3) {
            ListTag<cn.nukkit.nbt.tag.FloatTag> rangeList = new ListTag<>();
            rangeList.add(new cn.nukkit.nbt.tag.FloatTag("", food.onUseRange().get(0).floatValue()));
            rangeList.add(new cn.nukkit.nbt.tag.FloatTag("", food.onUseRange().get(1).floatValue()));
            rangeList.add(new cn.nukkit.nbt.tag.FloatTag("", food.onUseRange().get(2).floatValue()));
            foodTag.putList("on_use_range", rangeList);
        }

        nbt.putCompound("minecraft:food", foodTag);

        // minecraft:use_duration component (if present)
        if (components.getUseDuration() != null) {
            nbt.putInt("minecraft:use_duration", components.getUseDuration());
        }

        return nbt;
    }

    /**
     * Parse saturation modifier string to float value.
     * Maps Bedrock's named values to numeric coefficients.
     */
    private static float parseSaturationModifier(String modifier) {
        if (modifier == null) return 0.6f;
        return switch (modifier.toLowerCase()) {
            case "poor" -> 0.1f;
            case "low" -> 0.3f;
            case "normal" -> 0.6f;
            case "good" -> 0.8f;
            case "max" -> 1.0f;
            case "supernatural" -> 1.2f;
            default -> {
                // Try to parse as float
                try {
                    yield Float.parseFloat(modifier);
                } catch (NumberFormatException e) {
                    yield 0.6f;
                }
            }
        };
    }
}
