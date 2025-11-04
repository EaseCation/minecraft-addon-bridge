package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockProperties;
import cn.nukkit.block.BlockState;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.plugin.Plugin;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodCall;
import net.easecation.bridge.adapter.pnx.mapper.BlockDefinitionBuilder;

import static net.bytebuddy.matcher.ElementMatchers.isTypeInitializer;
import static net.bytebuddy.matcher.ElementMatchers.named;
import net.easecation.bridge.core.BlockDef;
import net.easecation.bridge.core.BridgeLoggerHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 使用ByteBuddy动态生成自定义Block类（简化版本）
 */
public class SimpleDynamicBlockGenerator {

    private final BlockDefinitionBuilder definitionBuilder;
    private final ByteBuddy byteBuddy;
    private final Plugin plugin;

    public SimpleDynamicBlockGenerator(Plugin plugin) {
        this.plugin = plugin;
        this.definitionBuilder = new BlockDefinitionBuilder();
        this.byteBuddy = new ByteBuddy();
        // 初始化Holder
        BlockDefinitionHolder.initialize(definitionBuilder);
    }

    /**
     * 为指定的BlockDef生成Block类
     *
     * @param blockDef Block定义
     * @return 生成的Block类
     */
    public Class<? extends Block> generateBlockClass(BlockDef blockDef) {
        try {
            // 注册BlockDef到Holder
            BlockDefinitionHolder.registerBlockDef(blockDef.id(), blockDef);

            // 生成类名
            String className = generateClassName(blockDef.id());
            BridgeLoggerHolder.getLogger().info("[SimpleDynamicBlockGenerator] Generating block class: " + className);

            // 使用ByteBuddy生成类
            DynamicType.Builder<Block> builder = byteBuddy
                .subclass(Block.class)
                .name(className)
                .implement(CustomBlock.class);

            // 定义PROPERTIES静态常量字段
            builder = builder.defineField("PROPERTIES", BlockProperties.class,
                Visibility.PUBLIC,
                net.bytebuddy.description.modifier.FieldManifestation.FINAL,
                net.bytebuddy.description.modifier.Ownership.STATIC);

            // 使用静态初始化器设置PROPERTIES字段的值
            // 等价于：public static final BlockProperties PROPERTIES = new BlockProperties(id);
            // 使用工厂方法避免ByteBuddy直接处理可变参数构造函数
            builder = builder.invokable(isTypeInitializer())
                .intercept(MethodCall.invoke(BlockPropertiesFactory.class.getMethod("create", String.class))
                    .with(blockDef.id())
                    .setsField(named("PROPERTIES")));

            // 无参构造函数 - PNX的BlockRegistry使用无参构造函数来实例化Block
            // ByteBuddy会自动生成Block_xxx(BlockState)构造函数来匹配父类，所以我们不需要手动定义
            // Block.getId()是final方法，会自动从getProperties().getIdentifier()返回正确的ID
            // 创建临时BlockProperties来获取defaultState（PROPERTIES字段已在静态初始化器中创建）
            BlockProperties tempProperties = new BlockProperties(blockDef.id());
            builder = builder.defineConstructor(Visibility.PUBLIC)
                .intercept(MethodCall.invoke(Block.class.getDeclaredConstructor(BlockState.class))
                    .onSuper()
                    .with(tempProperties.getDefaultState()));

            // 实现getProperties()方法
            builder = builder.defineMethod("getProperties", BlockProperties.class, Visibility.PUBLIC)
                .intercept(FieldAccessor.ofField("PROPERTIES"));

            // 实现getDefinition()方法
            builder = builder.defineMethod("getDefinition", CustomBlockDefinition.class, Visibility.PUBLIC)
                .intercept(MethodCall.invoke(BlockDefinitionHolder.class.getMethod("getDefinition", String.class, cn.nukkit.block.customblock.CustomBlock.class))
                    .with(blockDef.id())
                    .withThis());

            // 实现 CustomBlock 接口的其他方法
            // 注意：getId()方法不需要实现，因为Block.getId()是final方法，会自动满足接口要求

            // getFrictionFactor() - 默认 0.6
            builder = builder.defineMethod("getFrictionFactor", double.class, Visibility.PUBLIC)
                .intercept(net.bytebuddy.implementation.FixedValue.value(0.6));

            // getResistance() - 默认从 definition 获取，这里先返回默认值
            builder = builder.defineMethod("getResistance", double.class, Visibility.PUBLIC)
                .intercept(net.bytebuddy.implementation.FixedValue.value(1.0));

            // getLightFilter() - 默认 15 (完全阻挡光线)
            builder = builder.defineMethod("getLightFilter", int.class, Visibility.PUBLIC)
                .intercept(net.bytebuddy.implementation.FixedValue.value(15));

            // getLightLevel() - 默认 0 (不发光)
            builder = builder.defineMethod("getLightLevel", int.class, Visibility.PUBLIC)
                .intercept(net.bytebuddy.implementation.FixedValue.value(0));

            // getHardness() - 默认 1.0
            builder = builder.defineMethod("getHardness", double.class, Visibility.PUBLIC)
                .intercept(net.bytebuddy.implementation.FixedValue.value(1.0));

            // toItem() - 调用父类方法
            builder = builder.defineMethod("toItem", cn.nukkit.item.Item.class, Visibility.PUBLIC)
                .intercept(MethodCall.invoke(Block.class.getMethod("toItem")).onSuper());

            // 生成类并加载
            // IMPORTANT: Use INJECTION strategy to ensure visibility to PNX's BlockRegistry
            // INJECTION directly injects the class into plugin's ClassLoader without creating a child ClassLoader
            // PROPERTIES字段已在静态初始化器中设置，不需要后续反射操作
            Class<? extends Block> blockClass = builder
                .make()
                .load(plugin.getPluginClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

            BridgeLoggerHolder.getLogger().info("[SimpleDynamicBlockGenerator] Successfully generated block class: " + className);
            return blockClass;

        } catch (Exception e) {
            BridgeLoggerHolder.getLogger().error("[SimpleDynamicBlockGenerator] Failed to generate block class for: " + blockDef.id() + " - " + e.getMessage());
            throw new RuntimeException("Failed to generate block class for: " + blockDef.id(), e);
        }
    }

    /**
     * 生成类名
     */
    private String generateClassName(String blockId) {
        String safeName = blockId.replace(':', '_').replace('.', '_').replace('-', '_');
        return "net.easecation.bridge.adapter.pnx.generated.Block_" + safeName;
    }
}
