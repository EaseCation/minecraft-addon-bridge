package net.easecation.bridge.core.dto.item.v1_10;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Legacy模式物品定义 (format_version: 1.10)
 * <p>
 * 服务端只注册物品标识符，贴图和组件由客户端资源包提供
 * <p>
 * Legacy mode item definition (format_version: 1.10)
 * Server only registers item identifier, textures and components are provided by client resource pack
 *
 * @author EaseCation
 * @since 2025-01-31
 */
public record ItemsDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:item") Item minecraft_item
) {
}
