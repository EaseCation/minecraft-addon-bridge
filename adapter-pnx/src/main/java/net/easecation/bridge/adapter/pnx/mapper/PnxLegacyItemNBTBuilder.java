package net.easecation.bridge.adapter.pnx.mapper;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.ItemDef;

/**
 * Utility class for converting Legacy mode (v1.10) item components to NBT format for PowerNukkitX.
 *
 * <p>Legacy mode registration differs from Component-based mode:
 * <ul>
 *   <li>Legacy simple item: Empty components structure (no item_properties)</li>
 *   <li>Legacy food item: Only minecraft:food and minecraft:use_duration components</li>
 *   <li>Legacy item with netease components: Includes netease:armor, netease:weapon, etc.</li>
 *   <li>Component-based item: Full item_properties with all components</li>
 * </ul>
 *
 * <p>This implementation is based on easecation adapter's ItemComponentsNBT_Legacy
 * and maintains NBT format consistency across adapters.
 */
public class PnxLegacyItemNBTBuilder {

    /**
     * Build NBT for Legacy mode (v1.10) items.
     *
     * <p>Always returns a valid CompoundTag with a "components" field, even for simple items.
     * This ensures compatibility with CustomItemDefinition and ItemRegistryPacket encoding.
     *
     * @param itemDef The ItemDef containing Legacy components
     * @return CompoundTag with Legacy format NBT structure (never null)
     */
    public static CompoundTag buildNBT(ItemDef itemDef) {
        var components = itemDef.legacyComponents();

        if (components == null) {
            // Legacy items 需要有效的 CompoundTag，即使是空的
            // 根据 LEGACY_ITEM_REGISTRATION_GUIDE.md，即使没有组件也要返回 components 结构
            CompoundTag nbt = new CompoundTag();
            nbt.putCompound("components", new CompoundTag());
            return nbt;
        }

        CompoundTag nbt = new CompoundTag();
        boolean hasAnyComponent = false;

        // ========== Minecraft Components ==========

        // minecraft:food component
        if (components.getFood() != null) {
            buildFoodComponent(nbt, components.getFood());
            hasAnyComponent = true;
        }

        // minecraft:use_duration component
        if (components.getUseDuration() != null) {
            nbt.putInt("minecraft:use_duration", components.getUseDuration());
            hasAnyComponent = true;
        }

        // ========== Netease Components ==========

        // netease:armor component
        if (components.getNeteaseArmor() != null) {
            buildArmorComponent(nbt, components.getNeteaseArmor());
            hasAnyComponent = true;
        }

        // netease:weapon component
        if (components.getNeteaseWeapon() != null) {
            buildWeaponComponent(nbt, components.getNeteaseWeapon());
            hasAnyComponent = true;
        }

        // netease:allow_offhand component
        if (components.getNeteaseAllowOffhand() != null && components.getNeteaseAllowOffhand().value() != null) {
            CompoundTag allowOffhandTag = new CompoundTag();
            allowOffhandTag.putBoolean("value", components.getNeteaseAllowOffhand().value());
            nbt.putCompound("netease:allow_offhand", allowOffhandTag);
            hasAnyComponent = true;
        }

        // netease:fire_resistant component
        if (components.getNeteaseFireResistant() != null && components.getNeteaseFireResistant().value() != null) {
            CompoundTag fireResistantTag = new CompoundTag();
            fireResistantTag.putBoolean("value", components.getNeteaseFireResistant().value());
            nbt.putCompound("netease:fire_resistant", fireResistantTag);
            hasAnyComponent = true;
        }

        // netease:cooldown component
        if (components.getNeteaseCooldown() != null) {
            buildCooldownComponent(nbt, components.getNeteaseCooldown());
            hasAnyComponent = true;
        }

        // netease:customtips component
        if (components.getNeteaseCustomtips() != null && components.getNeteaseCustomtips().value() != null) {
            CompoundTag customtipsTag = new CompoundTag();
            customtipsTag.putString("value", components.getNeteaseCustomtips().value());
            nbt.putCompound("netease:customtips", customtipsTag);
            hasAnyComponent = true;
        }

        // netease:shield component
        if (components.getNeteaseShield() != null) {
            buildShieldComponent(nbt, components.getNeteaseShield());
            hasAnyComponent = true;
        }

        // netease:bucket component
        if (components.getNeteaseBucket() != null && components.getNeteaseBucket().fillLiquid() != null) {
            CompoundTag bucketTag = new CompoundTag();
            bucketTag.putString("fill_liquid", components.getNeteaseBucket().fillLiquid());
            nbt.putCompound("netease:bucket", bucketTag);
            hasAnyComponent = true;
        }

        // netease:egg component
        if (components.getNeteaseEgg() != null && components.getNeteaseEgg().entity() != null) {
            CompoundTag eggTag = new CompoundTag();
            eggTag.putString("entity", components.getNeteaseEgg().entity());
            nbt.putCompound("netease:egg", eggTag);
            hasAnyComponent = true;
        }

        // netease:fuel component
        if (components.getNeteaseFuel() != null && components.getNeteaseFuel().duration() != null) {
            CompoundTag fuelTag = new CompoundTag();
            fuelTag.putDouble("duration", components.getNeteaseFuel().duration());
            nbt.putCompound("netease:fuel", fuelTag);
            hasAnyComponent = true;
        }

        if (!hasAnyComponent) {
            // Legacy simple item - 返回空 components 结构
            // 根据 LEGACY_ITEM_REGISTRATION_GUIDE.md，即使没有组件也要有 components 字段
            nbt.putCompound("components", new CompoundTag());
        }

        return nbt;
    }

    /**
     * Build minecraft:food component.
     */
    private static void buildFoodComponent(CompoundTag nbt, net.easecation.bridge.core.dto.item.v1_10.Components.Food food) {
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
            rangeList.add(new cn.nukkit.nbt.tag.FloatTag(food.onUseRange().get(0).floatValue()));
            rangeList.add(new cn.nukkit.nbt.tag.FloatTag(food.onUseRange().get(1).floatValue()));
            rangeList.add(new cn.nukkit.nbt.tag.FloatTag(food.onUseRange().get(2).floatValue()));
            foodTag.putList("on_use_range", rangeList);
        }

        // effects (List<Effect> type)
        if (food.effects() != null && !food.effects().isEmpty()) {
            ListTag<CompoundTag> effectsList = new ListTag<>();
            for (var effect : food.effects()) {
                CompoundTag effectTag = new CompoundTag();
                if (effect.name() != null) {
                    effectTag.putString("name", effect.name());
                }
                if (effect.chance() != null) {
                    effectTag.putDouble("chance", effect.chance());
                }
                if (effect.duration() != null) {
                    effectTag.putInt("duration", effect.duration());
                }
                if (effect.amplifier() != null) {
                    effectTag.putInt("amplifier", effect.amplifier());
                }
                effectsList.add(effectTag);
            }
            foodTag.putList("effects", effectsList);
        }

        nbt.putCompound("minecraft:food", foodTag);
    }

    /**
     * Build netease:armor component.
     */
    private static void buildArmorComponent(CompoundTag nbt, net.easecation.bridge.core.dto.item.v1_10.Components.Armor armor) {
        CompoundTag armorTag = new CompoundTag();

        if (armor.defense() != null) {
            armorTag.putInt("defense", armor.defense());
        }

        if (armor.enchantment() != null) {
            armorTag.putInt("enchantment", armor.enchantment());
        }

        if (armor.armorSlot() != null) {
            armorTag.putInt("armor_slot", armor.armorSlot());
        }

        nbt.putCompound("netease:armor", armorTag);
    }

    /**
     * Build netease:weapon component.
     */
    private static void buildWeaponComponent(CompoundTag nbt, net.easecation.bridge.core.dto.item.v1_10.Components.Weapon weapon) {
        CompoundTag weaponTag = new CompoundTag();

        if (weapon.type() != null) {
            weaponTag.putString("type", weapon.type());
        }

        if (weapon.level() != null) {
            weaponTag.putInt("level", weapon.level());
        }

        if (weapon.speed() != null) {
            weaponTag.putInt("speed", weapon.speed());
        }

        if (weapon.attackDamage() != null) {
            weaponTag.putInt("attack_damage", weapon.attackDamage());
        }

        if (weapon.enchantment() != null) {
            weaponTag.putInt("enchantment", weapon.enchantment());
        }

        nbt.putCompound("netease:weapon", weaponTag);
    }

    /**
     * Build netease:cooldown component.
     */
    private static void buildCooldownComponent(CompoundTag nbt, net.easecation.bridge.core.dto.item.v1_10.Components.Cooldown cooldown) {
        CompoundTag cooldownTag = new CompoundTag();

        if (cooldown.category() != null) {
            cooldownTag.putString("category", cooldown.category());
        }

        if (cooldown.duration() != null) {
            cooldownTag.putInt("duration", cooldown.duration());
        }

        nbt.putCompound("netease:cooldown", cooldownTag);
    }

    /**
     * Build netease:shield component.
     */
    private static void buildShieldComponent(CompoundTag nbt, net.easecation.bridge.core.dto.item.v1_10.Components.Shield shield) {
        CompoundTag shieldTag = new CompoundTag();

        if (shield.defenceDamageSourceList() != null && !shield.defenceDamageSourceList().isEmpty()) {
            ListTag<cn.nukkit.nbt.tag.StringTag> defenceList = new ListTag<>();
            for (String source : shield.defenceDamageSourceList()) {
                defenceList.add(new cn.nukkit.nbt.tag.StringTag(source));
            }
            shieldTag.putList("defence_damage_source_list", defenceList);
        }

        if (shield.undefenceDamageSourceList() != null && !shield.undefenceDamageSourceList().isEmpty()) {
            ListTag<cn.nukkit.nbt.tag.StringTag> undefenceList = new ListTag<>();
            for (String source : shield.undefenceDamageSourceList()) {
                undefenceList.add(new cn.nukkit.nbt.tag.StringTag(source));
            }
            shieldTag.putList("undefence_damage_source_list", undefenceList);
        }

        if (shield.isConsumeDamage() != null) {
            shieldTag.putBoolean("is_consume_damage", shield.isConsumeDamage());
        }

        nbt.putCompound("netease:shield", shieldTag);
    }

    /**
     * Parse saturation modifier string to float value.
     * Maps Bedrock's named values to numeric coefficients.
     *
     * This implementation is identical to easecation adapter to maintain consistency.
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
                    BridgeLoggerHolder.getLogger().warning(
                        "[PnxLegacyItemNBTBuilder] Unknown saturation modifier: " + modifier + ", using default 0.6f");
                    yield 0.6f;
                }
            }
        };
    }
}
