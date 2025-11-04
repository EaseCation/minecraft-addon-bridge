package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.custom.CustomEntity;
import cn.nukkit.entity.custom.CustomEntityDefinition;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.Plugin;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;
import net.easecation.bridge.adapter.pnx.mapper.EntityDefinitionBuilder;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.EntityDef;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

/**
 * 使用ByteBuddy动态生成自定义Entity类（简化版本）
 */
public class SimpleDynamicEntityGenerator {

    private final EntityDefinitionBuilder definitionBuilder;
    private final ByteBuddy byteBuddy;
    private final Plugin plugin;

    public SimpleDynamicEntityGenerator(Plugin plugin) {
        this.plugin = plugin;
        this.definitionBuilder = new EntityDefinitionBuilder();
        this.byteBuddy = new ByteBuddy();
        // 初始化Holder
        EntityDefinitionHolder.initialize(definitionBuilder);
    }

    /**
     * 为指定的EntityDef生成Entity类
     *
     * @param entityDef Entity定义
     * @return 生成的Entity类
     */
    public Class<? extends Entity> generateEntityClass(EntityDef entityDef) {
        try {
            // 注册EntityDef到Holder
            EntityDefinitionHolder.registerEntityDef(entityDef.id(), entityDef);

            // 生成类名
            String className = generateClassName(entityDef.id());
            BridgeLoggerHolder.getLogger().info("[SimpleDynamicEntityGenerator] Generating entity class: " + className);

            // 使用ByteBuddy生成类
            DynamicType.Builder<Entity> builder = byteBuddy
                .subclass(Entity.class)
                .name(className)
                .implement(CustomEntity.class);

            // 实现getIdentifier()方法 - 直接返回entity ID常量，无需字段或构造函数
            builder = builder.defineMethod("getIdentifier", String.class, Visibility.PUBLIC)
                .intercept(FixedValue.value(entityDef.id()));

            // 添加静态方法getDefinition() - PNX的EntityRegistry要求这个来解析CustomEntityDefinition
            // PNX只需要静态方法，不需要实例方法（CustomEntity接口本身是空接口）
            builder = builder.defineMethod("getDefinition", CustomEntityDefinition.class,
                    Visibility.PUBLIC, net.bytebuddy.description.modifier.Ownership.STATIC)
                .intercept(MethodCall.invoke(EntityDefinitionHolder.class.getMethod("getDefinition", String.class))
                    .with(entityDef.id()));

            // 重写initEntity()方法来应用简单属性
            builder = builder.defineMethod("initEntity", void.class, Visibility.PROTECTED)
                .intercept(MethodDelegation.to(new InitEntityInterceptor(entityDef.id())));

            // 生成类并加载
            // IMPORTANT: Use INJECTION strategy to ensure visibility to PNX's EntityRegistry
            // INJECTION directly injects the class into plugin's ClassLoader without creating a child ClassLoader
            Class<? extends Entity> entityClass = builder
                .make()
                .load(plugin.getPluginClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

            BridgeLoggerHolder.getLogger().info("[SimpleDynamicEntityGenerator] Successfully generated entity class: " + className);
            return entityClass;

        } catch (Exception e) {
            BridgeLoggerHolder.getLogger().error("[SimpleDynamicEntityGenerator] Failed to generate entity class for: " + entityDef.id() + " - " + e.getMessage());
            throw new RuntimeException("Failed to generate entity class for: " + entityDef.id(), e);
        }
    }

    /**
     * 生成类名
     */
    private String generateClassName(String entityId) {
        String safeName = entityId.replace(':', '_').replace('.', '_').replace('-', '_');
        return "net.easecation.bridge.adapter.pnx.generated.Entity_" + safeName;
    }

    /**
     * initEntity()方法拦截器
     * 在实体初始化时调用EntityPropertyApplier来应用简单属性
     */
    public static class InitEntityInterceptor {
        private final String entityId;

        public InitEntityInterceptor(String entityId) {
            this.entityId = entityId;
        }

        public void initEntity(@This Entity entity, @SuperCall Callable<Void> superCall) throws Exception {
            // 先调用父类的initEntity()
            superCall.call();

            // 从EntityDefinitionHolder获取EntityDef
            EntityDef entityDef = EntityDefinitionHolder.getEntityDef(entityId);
            if (entityDef != null) {
                // 应用简单属性
                EntityPropertyApplier.applyProperties(entity, entityDef);
                BridgeLoggerHolder.getLogger().info("[SimpleDynamicEntityGenerator] Applied properties for entity: " + entityId);
            } else {
                BridgeLoggerHolder.getLogger().warning("[SimpleDynamicEntityGenerator] EntityDef not found for: " + entityId);
            }
        }
    }
}
