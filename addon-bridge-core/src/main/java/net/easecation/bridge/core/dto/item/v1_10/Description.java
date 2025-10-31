package net.easecation.bridge.core.dto.item.v1_10;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Legacy模式物品描述
 * <p>
 * Legacy mode item description
 *
 * @author EaseCation
 * @since 2025-01-31
 */
public record Description(
    /**
     * 物品标识符，包括命名空间及物品名
     * <p>
     * Item identifier including namespace and item name
     */
    @JsonProperty("identifier") String identifier,

    /**
     * 注册分类
     * <p>
     * 可选值：construction（建筑）、equipment（装备）、items（物品）、
     * nature（自然）、commands（仅指令和API可获取）、none（仅API可获取）
     * <p>
     * Registration category
     * Available values: construction, equipment, items, nature, commands, none
     */
    @JsonProperty("category") String category,

    /**
     * 自定义物品类别
     * <p>
     * 可选值：weapon、armor、egg、ranged_weapon、bucket、projectile_item、shield
     * <p>
     * Custom item type
     * Available values: weapon, armor, egg, ranged_weapon, bucket, projectile_item, shield
     */
    @JsonProperty("custom_item_type") String customItemType
) {
}
