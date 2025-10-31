package net.easecation.bridge.core;

import net.easecation.bridge.core.dto.item.v1_21_60.Item;

import javax.annotation.Nullable;

/**
 * Represents a custom item definition parsed from behavior pack.
 * Supports both Legacy (v1.10) and Component-based (v1.19+) registration modes.
 * <p>
 * Legacy物品使用legacyDescription和legacyComponents字段。
 * Component-based物品使用componentDescription和componentComponents字段，并自动升级到最新版本(v1_21_60)。
 */
public record ItemDef(
        String id,
        ItemRegistrationMode registrationMode,

        // Component-based模式字段（1.19+）
        @Nullable Item.Description componentDescription,
        @Nullable Item.Components componentComponents,

        // Legacy模式字段（1.10）
        @Nullable net.easecation.bridge.core.dto.item.v1_10.Description legacyDescription,
        @Nullable net.easecation.bridge.core.dto.item.v1_10.Components legacyComponents
) {
    /**
     * 物品注册模式
     * <p>
     * Item registration mode
     */
    public enum ItemRegistrationMode {
        /**
         * Legacy模式 (version=0, format_version=1.10): 服务端只注册物品标识符，贴图和组件由客户端资源包提供
         * <p>
         * Legacy mode (version=0): Server only registers item identifier, textures and components are provided by client resource pack
         */
        LEGACY,

        /**
         * Component-based模式 (version=1, format_version=1.19+): 服务端通过NBT完全定义物品的属性和行为
         * <p>
         * Component-based mode (version=1): Server fully defines item properties and behavior through NBT
         */
        COMPONENT_BASED
    }

    /**
     * 从Component-based DTO创建（现有逻辑，重命名）
     * <p>
     * Create from Component-based DTO (existing logic, renamed)
     */
    public static ItemDef fromComponentBasedDTO(Item item) {
        String identifier = item.description() != null && item.description().identifier() != null
                ? item.description().identifier()
                : "unknown:item";

        return new ItemDef(
                identifier,
                ItemRegistrationMode.COMPONENT_BASED,
                item.description(),
                item.components(),
                null,  // Legacy字段为null
                null
        );
    }

    /**
     * 从Legacy DTO创建（新增）
     * <p>
     * Create from Legacy DTO (new)
     */
    public static ItemDef fromLegacyDTO(net.easecation.bridge.core.dto.item.v1_10.Item item) {
        String identifier = item.description() != null && item.description().identifier() != null
                ? item.description().identifier()
                : "unknown:item";

        return new ItemDef(
                identifier,
                ItemRegistrationMode.LEGACY,
                null,  // Component字段为null
                null,
                item.description(),
                item.components()
        );
    }

    /**
     * 从DTO创建（保持向后兼容）
     * <p>
     * Create from DTO (backward compatibility)
     *
     * @deprecated 使用 {@link #fromComponentBasedDTO(Item)} 代替
     */
    @Deprecated
    public static ItemDef fromDTO(Item dto) {
        return fromComponentBasedDTO(dto);
    }

    /**
     * 判断是否为Legacy模式
     * <p>
     * Check if this is Legacy mode
     */
    public boolean isLegacy() {
        return registrationMode == ItemRegistrationMode.LEGACY;
    }

    /**
     * 获取菜单分类（兼容两种模式）
     * <p>
     * Get menu category (compatible with both modes)
     */
    @Nullable
    public String getMenuCategory() {
        if (isLegacy()) {
            return legacyDescription != null ? legacyDescription.category() : null;
        } else {
            var menuCategory = componentDescription != null ? componentDescription.menuCategory() : null;
            return menuCategory != null ? menuCategory.category() : null;
        }
    }

    /**
     * 获取菜单分类对象（仅Component-based模式）
     * <p>
     * Get menu category object (Component-based mode only)
     */
    @Nullable
    public Item.Description.MenuCategory menuCategory() {
        return componentDescription != null ? componentDescription.menuCategory() : null;
    }

    // 便捷访问器

    /**
     * 获取物品描述（根据模式返回对应的描述对象）
     */
    @Nullable
    public Object getDescription() {
        return isLegacy() ? legacyDescription : componentDescription;
    }

    /**
     * 获取物品组件（根据模式返回对应的组件对象）
     */
    @Nullable
    public Object getComponents() {
        return isLegacy() ? legacyComponents : componentComponents;
    }
}

