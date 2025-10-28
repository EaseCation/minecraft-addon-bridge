package net.easecation.bridge.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 表示 JSON Schema 中定义的空对象（type: object 但没有 properties）。
 *
 * 这个类型用于与未能解析的 Object 类型做出区分：
 * - EmptyObject: Schema 明确定义为空对象
 * - Object: 无法确定具体类型或动态类型
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmptyObject() {
    public static final EmptyObject INSTANCE = new EmptyObject();
}
