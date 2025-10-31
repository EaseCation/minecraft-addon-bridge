package net.easecation.bridge.core.dto.item.v1_10;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Legacy模式组件 - 扁平结构
 * <p>
 * 直接包含 minecraft:xxx 和 netease:xxx 组件，无嵌套的 item_properties 结构
 * <p>
 * Legacy mode components - flat structure
 * Directly contains minecraft:xxx and netease:xxx components, without nested item_properties structure
 *
 * @author EaseCation
 * @since 2025-01-31
 */
public class Components {

    // ========== 原版 Minecraft Components ==========

    /**
     * 使物品拥有附魔光效
     */
    @JsonProperty("minecraft:foil")
    private Boolean foil;

    /**
     * 食物组件
     */
    @JsonProperty("minecraft:food")
    private Food food;

    /**
     * 使手持物品按武器的方式渲染
     */
    @JsonProperty("minecraft:hand_equipped")
    private Boolean handEquipped;

    /**
     * 物品悬浮文本的颜色（资源包组件）
     */
    @JsonProperty("minecraft:hover_text_color")
    private String hoverTextColor;

    /**
     * 物品的贴图（资源包组件）
     */
    @JsonProperty("minecraft:icon")
    private String icon;

    /**
     * 物品耐久度
     */
    @JsonProperty("minecraft:max_damage")
    private Integer maxDamage;

    /**
     * 物品最大堆叠数量
     */
    @JsonProperty("minecraft:max_stack_size")
    private MaxStackSize maxStackSize;

    /**
     * 农作物种子属性
     */
    @JsonProperty("minecraft:seed")
    private Seed seed;

    /**
     * 不同aux值的物品是否能够堆叠
     */
    @JsonProperty("minecraft:stacked_by_data")
    private Boolean stackedByData;

    /**
     * 物品标签
     */
    @JsonProperty("minecraft:tags")
    private Tags tags;

    /**
     * 使用物品时播放的动画（资源包组件）
     */
    @JsonProperty("minecraft:use_animation")
    private String useAnimation;

    /**
     * 物品最大使用时长
     */
    @JsonProperty("minecraft:use_duration")
    private Integer useDuration;

    // ========== 网易 Netease Components ==========

    /**
     * 物品是否可以放在副手
     */
    @JsonProperty("netease:allow_offhand")
    private AllowOffhand neteaseAllowOffhand;

    /**
     * 自定义盔甲组件
     */
    @JsonProperty("netease:armor")
    private Armor neteaseArmor;

    /**
     * 自定义桶组件
     */
    @JsonProperty("netease:bucket")
    private Bucket neteaseBucket;

    /**
     * 物品是否可以堆肥
     */
    @JsonProperty("netease:compostable")
    private Integer neteaseCompostable;

    /**
     * 物品冷却时间
     */
    @JsonProperty("netease:cooldown")
    private Cooldown neteaseCooldown;

    /**
     * 物品描述信息
     */
    @JsonProperty("netease:customtips")
    private Customtips neteaseCustomtips;

    /**
     * 自定义生物蛋组件
     */
    @JsonProperty("netease:egg")
    private Egg neteaseEgg;

    /**
     * 物品是否可以当附魔材料
     */
    @JsonProperty("netease:enchant_material")
    private EnchantMaterial neteaseEnchantMaterial;

    /**
     * 物品是否防火
     */
    @JsonProperty("netease:fire_resistant")
    private FireResistant neteaseFireResistant;

    /**
     * 物品贴图序列帧动画（资源包组件）
     */
    @JsonProperty("netease:frame_anim_in_scene")
    private FrameAnimInScene neteaseFrameAnimInScene;

    /**
     * 自定义蓄力物品（资源包组件）
     */
    @JsonProperty("netease:frame_animation")
    private FrameAnimation neteaseFrameAnimation;

    /**
     * 物品的燃料属性
     */
    @JsonProperty("netease:fuel")
    private Fuel neteaseFuel;

    /**
     * 物品初始userData
     */
    @JsonProperty("netease:initial_user_data")
    private Map<String, Object> neteaseInitialUserData;

    /**
     * 自定义抛射物组件
     */
    @JsonProperty("netease:projectile")
    private String neteaseProjectile;

    /**
     * 右手物品的渲染偏移
     */
    @JsonProperty("netease:render_offsets")
    private RenderOffsets neteaseRenderOffsets;

    /**
     * 自定义盾牌组件
     */
    @JsonProperty("netease:shield")
    private Shield neteaseShield;

    /**
     * 物品拿在手上时是否显示
     */
    @JsonProperty("netease:show_in_hand")
    private ShowInHand neteaseShowInHand;

    /**
     * 自定义武器组件
     */
    @JsonProperty("netease:weapon")
    private Weapon neteaseWeapon;

    /**
     * 其他未知组件使用Map存储
     */
    private Map<String, Object> additionalComponents = new HashMap<>();

    // ========== 嵌套记录类定义 ==========

    /**
     * 食物组件
     */
    public record Food(
        @JsonProperty("can_always_eat") Boolean canAlwaysEat,
        @JsonProperty("nutrition") Integer nutrition,
        @JsonProperty("saturation_modifier") String saturationModifier,
        @JsonProperty("using_converts_to") String usingConvertsTo,
        @JsonProperty("on_use_action") String onUseAction,
        @JsonProperty("on_use_range") List<Integer> onUseRange,
        @JsonProperty("cooldown_type") String cooldownType,
        @JsonProperty("cooldown_time") Integer cooldownTime,
        @JsonProperty("effects") List<Effect> effects
    ) {
        public record Effect(
            @JsonProperty("name") String name,
            @JsonProperty("chance") Double chance,
            @JsonProperty("duration") Integer duration,
            @JsonProperty("amplifier") Integer amplifier
        ) {}
    }

    /**
     * 最大堆叠数量
     */
    public record MaxStackSize(
        @JsonProperty("value") Integer value
    ) {}

    /**
     * 种子属性
     */
    public record Seed(
        @JsonProperty("crop_result") String cropResult,
        @JsonProperty("plant_at") List<String> plantAt
    ) {}

    /**
     * 标签
     */
    public record Tags(
        @JsonProperty("tags") List<String> tags
    ) {}

    /**
     * 副手设置
     */
    public record AllowOffhand(
        @JsonProperty("value") Boolean value
    ) {}

    /**
     * 盔甲属性
     */
    public record Armor(
        @JsonProperty("defense") Integer defense,
        @JsonProperty("enchantment") Integer enchantment,
        @JsonProperty("armor_slot") Integer armorSlot
    ) {}

    /**
     * 桶属性
     */
    public record Bucket(
        @JsonProperty("fill_liquid") String fillLiquid
    ) {}

    /**
     * 冷却设置
     */
    public record Cooldown(
        @JsonProperty("category") String category,
        @JsonProperty("duration") Integer duration
    ) {}

    /**
     * 自定义提示
     */
    public record Customtips(
        @JsonProperty("value") String value
    ) {}

    /**
     * 生物蛋
     */
    public record Egg(
        @JsonProperty("entity") String entity
    ) {}

    /**
     * 附魔材料
     */
    public record EnchantMaterial(
        @JsonProperty("value") Boolean value
    ) {}

    /**
     * 防火属性
     */
    public record FireResistant(
        @JsonProperty("value") Boolean value
    ) {}

    /**
     * 场景序列帧动画
     */
    public record FrameAnimInScene(
        @JsonProperty("texture_path") String texturePath,
        @JsonProperty("ticks_per_frame") Integer ticksPerFrame
    ) {}

    /**
     * 序列帧动画
     */
    public record FrameAnimation(
        @JsonProperty("frame_count") Integer frameCount,
        @JsonProperty("texture_name") String textureName,
        @JsonProperty("animate_in_toolbar") Boolean animateInToolbar
    ) {}

    /**
     * 燃料属性
     */
    public record Fuel(
        @JsonProperty("duration") Double duration
    ) {}

    /**
     * 渲染偏移
     */
    public record RenderOffsets(
        @JsonProperty("controller_position_adjust") List<Double> controllerPositionAdjust,
        @JsonProperty("controller_rotation_adjust") List<Double> controllerRotationAdjust,
        @JsonProperty("controller_scale") Double controllerScale
    ) {}

    /**
     * 盾牌属性
     */
    public record Shield(
        @JsonProperty("defence_damage_source_list") List<String> defenceDamageSourceList,
        @JsonProperty("undefence_damage_source_list") List<String> undefenceDamageSourceList,
        @JsonProperty("is_consume_damage") Boolean isConsumeDamage
    ) {}

    /**
     * 手持显示
     */
    public record ShowInHand(
        @JsonProperty("value") Boolean value
    ) {}

    /**
     * 武器属性
     */
    public record Weapon(
        @JsonProperty("type") String type,
        @JsonProperty("level") Integer level,
        @JsonProperty("speed") Integer speed,
        @JsonProperty("attack_damage") Integer attackDamage,
        @JsonProperty("enchantment") Integer enchantment
    ) {}

    // ========== Getter和Setter ==========

    @JsonAnySetter
    public void setAdditionalComponent(String key, Object value) {
        additionalComponents.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalComponents() {
        return additionalComponents;
    }

    // Getters for all fields
    public Boolean getFoil() { return foil; }
    public Food getFood() { return food; }
    public Boolean getHandEquipped() { return handEquipped; }
    public String getHoverTextColor() { return hoverTextColor; }
    public String getIcon() { return icon; }
    public Integer getMaxDamage() { return maxDamage; }
    public MaxStackSize getMaxStackSize() { return maxStackSize; }
    public Seed getSeed() { return seed; }
    public Boolean getStackedByData() { return stackedByData; }
    public Tags getTags() { return tags; }
    public String getUseAnimation() { return useAnimation; }
    public Integer getUseDuration() { return useDuration; }
    public AllowOffhand getNeteaseAllowOffhand() { return neteaseAllowOffhand; }
    public Armor getNeteaseArmor() { return neteaseArmor; }
    public Bucket getNeteaseBucket() { return neteaseBucket; }
    public Integer getNeteaseCompostable() { return neteaseCompostable; }
    public Cooldown getNeteaseCooldown() { return neteaseCooldown; }
    public Customtips getNeteaseCustomtips() { return neteaseCustomtips; }
    public Egg getNeteaseEgg() { return neteaseEgg; }
    public EnchantMaterial getNeteaseEnchantMaterial() { return neteaseEnchantMaterial; }
    public FireResistant getNeteaseFireResistant() { return neteaseFireResistant; }
    public FrameAnimInScene getNeteaseFrameAnimInScene() { return neteaseFrameAnimInScene; }
    public FrameAnimation getNeteaseFrameAnimation() { return neteaseFrameAnimation; }
    public Fuel getNeteaseFuel() { return neteaseFuel; }
    public Map<String, Object> getNeteaseInitialUserData() { return neteaseInitialUserData; }
    public String getNeteaseProjectile() { return neteaseProjectile; }
    public RenderOffsets getNeteaseRenderOffsets() { return neteaseRenderOffsets; }
    public Shield getNeteaseShield() { return neteaseShield; }
    public ShowInHand getNeteaseShowInHand() { return neteaseShowInHand; }
    public Weapon getNeteaseWeapon() { return neteaseWeapon; }
}
