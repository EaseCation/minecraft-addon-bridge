package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.item.Item;
import cn.nukkit.item.customitem.CustomItem;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.plugin.Plugin;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodCall;
import net.easecation.bridge.adapter.pnx.mapper.ItemDefinitionBuilder;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.ItemDef;

/**
 * 使用ByteBuddy动态生成自定义Item类（简化版本）
 */
public class SimpleDynamicItemGenerator {

    private final ItemDefinitionBuilder definitionBuilder;
    private final ByteBuddy byteBuddy;
    private final Plugin plugin;

    public SimpleDynamicItemGenerator(Plugin plugin) {
        this.plugin = plugin;
        this.definitionBuilder = new ItemDefinitionBuilder();
        this.byteBuddy = new ByteBuddy();
        // 初始化Holder
        ItemDefinitionHolder.initialize(definitionBuilder);
    }

    /**
     * 为指定的ItemDef生成Item类
     *
     * @param itemDef Item定义
     * @return 生成的Item类
     */
    public Class<? extends Item> generateItemClass(ItemDef itemDef) {
        try {
            // 注册ItemDef到Holder
            ItemDefinitionHolder.registerItemDef(itemDef.id(), itemDef);

            // 生成类名
            String className = generateClassName(itemDef.id());
            BridgeLoggerHolder.getLogger().info("[SimpleDynamicItemGenerator] Generating item class: " + className);

            // 使用ByteBuddy生成类
            DynamicType.Builder<Item> builder = byteBuddy
                .subclass(Item.class)
                .name(className)
                .implement(CustomItem.class);

            // 添加itemId字段
            builder = builder.defineField("itemId", String.class,
                Visibility.PRIVATE,
                net.bytebuddy.description.modifier.FieldManifestation.FINAL);

            // 无参构造函数
            builder = builder.defineConstructor(Visibility.PUBLIC)
                .intercept(MethodCall.invoke(Item.class.getDeclaredConstructor(String.class))
                    .onSuper()
                    .with(itemDef.id())
                    .andThen(FieldAccessor.ofField("itemId").setsValue(itemDef.id())));

            // 实现getDefinition()方法
            // 注意：由于泛型擦除，getDefinition的泛型参数<T extends Item & CustomItem>会被擦除为Item
            builder = builder.defineMethod("getDefinition", CustomItemDefinition.class, Visibility.PUBLIC)
                .intercept(MethodCall.invoke(ItemDefinitionHolder.class.getMethod("getDefinition", String.class, Item.class))
                    .with(itemDef.id())
                    .withThis());

            // 生成类并加载
            // IMPORTANT: Use INJECTION strategy to ensure visibility to PNX's FastConstructor
            // INJECTION directly injects the class into plugin's ClassLoader without creating a child ClassLoader
            Class<? extends Item> itemClass = builder
                .make()
                .load(plugin.getPluginClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

            BridgeLoggerHolder.getLogger().info("[SimpleDynamicItemGenerator] Successfully generated item class: " + className);
            return itemClass;

        } catch (Exception e) {
            BridgeLoggerHolder.getLogger().error("[SimpleDynamicItemGenerator] Failed to generate item class for: " + itemDef.id() + " - " + e.getMessage());
            throw new RuntimeException("Failed to generate item class for: " + itemDef.id(), e);
        }
    }

    /**
     * 生成类名
     */
    private String generateClassName(String itemId) {
        String safeName = itemId.replace(':', '_').replace('.', '_').replace('-', '_');
        return "net.easecation.bridge.adapter.pnx.generated.Item_" + safeName;
    }
}
