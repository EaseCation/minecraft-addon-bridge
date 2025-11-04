package net.easecation.bridge.adapter.pnx.mapper;

import org.jetbrains.annotations.Nullable;

/**
 * 组件映射器接口
 *
 * @param <TComponent> DTO组件类型（来自addon-bridge-core）
 * @param <TBuilder> PNX Builder类型（如CustomBlockDefinition.Builder）
 */
@FunctionalInterface
public interface ComponentMapper<TComponent, TBuilder> {

    /**
     * 将DTO组件应用到PNX Builder
     *
     * @param component DTO组件对象（可能为null）
     * @param builder PNX Builder对象
     * @param context 映射上下文，用于记录警告和未映射的组件
     */
    void apply(@Nullable TComponent component, TBuilder builder, MappingContext context);

    /**
     * 组合多个映射器
     */
    default ComponentMapper<TComponent, TBuilder> andThen(ComponentMapper<TComponent, TBuilder> after) {
        return (component, builder, context) -> {
            this.apply(component, builder, context);
            after.apply(component, builder, context);
        };
    }
}
