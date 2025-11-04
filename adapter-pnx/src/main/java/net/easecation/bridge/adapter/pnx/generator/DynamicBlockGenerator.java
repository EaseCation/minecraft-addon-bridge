package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockProperties;
import cn.nukkit.block.BlockState;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.This;
import net.easecation.bridge.adapter.pnx.mapper.BlockDefinitionBuilder;
import net.easecation.bridge.core.BlockDef;
import net.easecation.bridge.core.BridgeLoggerHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 使用ByteBuddy动态生成自定义Block类
 */
public class DynamicBlockGenerator {

    private final BlockDefinitionBuilder definitionBuilder;
    private final ByteBuddy byteBuddy;

    public DynamicBlockGenerator() {
        this.definitionBuilder = new BlockDefinitionBuilder();
        this.byteBuddy = new ByteBuddy();
    }

    /**
     * 为指定的BlockDef生成Block类
     *
     * @param blockDef Block定义
     * @return 生成的Block类
     */
    public Class<? extends Block> generateBlockClass(BlockDef blockDef) {
        try {
            // 生成类名，例如：DynamicBlock_minecraft_stone
            String className = generateClassName(blockDef.id());

            BridgeLoggerHolder.getLogger().info("[DynamicBlockGenerator] Generating block class: " + className);

            // 使用ByteBuddy生成类
            DynamicType.Builder<Block> builder = byteBuddy
                .subclass(Block.class)
                .name(className)
                .implement(CustomBlock.class)
                // 添加PROPERTIES静态字段
                .defineField("PROPERTIES", BlockProperties.class, Visibility.PUBLIC, net.bytebuddy.description.modifier.FieldManifestation.FINAL, net.bytebuddy.description.modifier.Ownership.STATIC)
                // 添加blockDef字段，用于getDefinition()
                .defineField("blockDef", BlockDef.class, Visibility.PRIVATE, net.bytebuddy.description.modifier.FieldManifestation.FINAL)
                .defineField("definitionBuilder", BlockDefinitionBuilder.class, Visibility.PRIVATE, net.bytebuddy.description.modifier.FieldManifestation.FINAL);

            // 构造函数1: 无参构造
            builder = builder.defineConstructor(Visibility.PUBLIC)
                .intercept(MethodDelegation.to(new ConstructorInterceptor(blockDef, definitionBuilder)));

            // 构造函数2: BlockState参数构造
            builder = builder.defineConstructor(Visibility.PUBLIC)
                .withParameters(BlockState.class)
                .intercept(MethodDelegation.to(new ConstructorWithStateInterceptor(blockDef, definitionBuilder)));

            // 实现getProperties()方法
            builder = builder.defineMethod("getProperties", BlockProperties.class, Visibility.PUBLIC)
                .intercept(MethodDelegation.to(new GetPropertiesInterceptor()));

            // 实现getDefinition()方法
            builder = builder.defineMethod("getDefinition", CustomBlockDefinition.class, Visibility.PUBLIC)
                .intercept(MethodDelegation.to(new GetDefinitionInterceptor()));

            // 生成类并加载
            Class<? extends Block> blockClass = builder
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();

            // 初始化PROPERTIES静态字段
            initializeStaticFields(blockClass, blockDef);

            BridgeLoggerHolder.getLogger().info("[DynamicBlockGenerator] Successfully generated block class: " + className);
            return blockClass;

        } catch (Exception e) {
            BridgeLoggerHolder.getLogger().error("[DynamicBlockGenerator] Failed to generate block class for: " + blockDef.id() + " - " + e.getMessage());
            throw new RuntimeException("Failed to generate block class for: " + blockDef.id(), e);
        }
    }

    /**
     * 生成类名
     */
    private String generateClassName(String blockId) {
        // 将 "minecraft:stone" 转换为 "DynamicBlock_minecraft_stone"
        String safeName = blockId.replace(':', '_').replace('.', '_').replace('-', '_');
        return "net.easecation.bridge.adapter.pnx.generated.DynamicBlock_" + safeName;
    }

    /**
     * 初始化静态字段
     */
    private void initializeStaticFields(Class<? extends Block> blockClass, BlockDef blockDef) throws Exception {
        java.lang.reflect.Field propertiesField = blockClass.getDeclaredField("PROPERTIES");
        propertiesField.setAccessible(true);
        propertiesField.set(null, new BlockProperties(blockDef.id()));
    }

    // ========== ByteBuddy拦截器 ==========

    /**
     * 无参构造函数拦截器
     */
    public static class ConstructorInterceptor {
        private final BlockDef blockDef;
        private final BlockDefinitionBuilder definitionBuilder;

        public ConstructorInterceptor(BlockDef blockDef, BlockDefinitionBuilder definitionBuilder) {
            this.blockDef = blockDef;
            this.definitionBuilder = definitionBuilder;
        }

        public void construct(@This Block self) throws Exception {
            // 调用父类构造函数
            // 注意：ByteBuddy会自动处理super()调用，这里主要是设置字段
            java.lang.reflect.Field propertiesField = self.getClass().getDeclaredField("PROPERTIES");
            propertiesField.setAccessible(true);
            BlockProperties properties = (BlockProperties) propertiesField.get(null);

            // 设置blockDef字段
            java.lang.reflect.Field blockDefField = self.getClass().getDeclaredField("blockDef");
            blockDefField.setAccessible(true);
            blockDefField.set(self, blockDef);

            // 设置definitionBuilder字段
            java.lang.reflect.Field builderField = self.getClass().getDeclaredField("definitionBuilder");
            builderField.setAccessible(true);
            builderField.set(self, definitionBuilder);
        }
    }

    /**
     * 带BlockState参数的构造函数拦截器
     */
    public static class ConstructorWithStateInterceptor {
        private final BlockDef blockDef;
        private final BlockDefinitionBuilder definitionBuilder;

        public ConstructorWithStateInterceptor(BlockDef blockDef, BlockDefinitionBuilder definitionBuilder) {
            this.blockDef = blockDef;
            this.definitionBuilder = definitionBuilder;
        }

        public void construct(@This Block self, @Nullable BlockState blockState) throws Exception {
            // 设置blockDef字段
            java.lang.reflect.Field blockDefField = self.getClass().getDeclaredField("blockDef");
            blockDefField.setAccessible(true);
            blockDefField.set(self, blockDef);

            // 设置definitionBuilder字段
            java.lang.reflect.Field builderField = self.getClass().getDeclaredField("definitionBuilder");
            builderField.setAccessible(true);
            builderField.set(self, definitionBuilder);
        }
    }

    /**
     * getProperties()方法拦截器
     */
    public static class GetPropertiesInterceptor {
        @NotNull
        public BlockProperties getProperties(@This Block self) throws Exception {
            java.lang.reflect.Field propertiesField = self.getClass().getDeclaredField("PROPERTIES");
            propertiesField.setAccessible(true);
            return (BlockProperties) propertiesField.get(null);
        }
    }

    /**
     * getDefinition()方法拦截器
     */
    public static class GetDefinitionInterceptor {
        public CustomBlockDefinition getDefinition(@This Block self) throws Exception {
            java.lang.reflect.Field blockDefField = self.getClass().getDeclaredField("blockDef");
            blockDefField.setAccessible(true);
            BlockDef blockDef = (BlockDef) blockDefField.get(self);

            java.lang.reflect.Field builderField = self.getClass().getDeclaredField("definitionBuilder");
            builderField.setAccessible(true);
            BlockDefinitionBuilder builder = (BlockDefinitionBuilder) builderField.get(self);

            // 生成的 Block 类实现了 CustomBlock 接口，需要强制转换
            return builder.build(blockDef, (cn.nukkit.block.customblock.CustomBlock) self);
        }
    }
}
