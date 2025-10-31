package net.easecation.bridge.adapter.mot.item;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.easecation.bridge.adapter.mot.util.IdentifierSanitizer;
import net.easecation.bridge.core.ItemDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dynamic class generator for ItemDataDriven.
 * Generates a unique subclass of ItemDataDriven for each custom item.
 *
 * <p>This is necessary because MOT's Item.registerCustomItem(Class) requires
 * a separate class for each custom item, unlike EaseCation's factory-based approach.</p>
 */
public class DynamicItemClassGenerator {

    // Cache of generated classes to avoid regeneration
    private static final Map<String, Class<? extends ItemDataDriven>> GENERATED_CLASSES = new ConcurrentHashMap<>();

    /**
     * Generate a unique ItemDataDriven subclass for the given ItemDef.
     *
     * <p>The generated class will:
     * <ul>
     *   <li>Extend ItemDataDriven</li>
     *   <li>Have a unique name based on the item identifier</li>
     *   <li>Call super constructor with identifier, displayName, and textureName</li>
     *   <li>Be cached to avoid regeneration</li>
     * </ul>
     *
     * @param itemDef The ItemDef to generate a class for
     * @param textureName The texture name for the item
     * @return A dynamically generated class extending ItemDataDriven
     */
    public static Class<? extends ItemDataDriven> generateItemClass(ItemDef itemDef, String textureName) {
        String identifier = itemDef.id();

        // Check cache first
        Class<? extends ItemDataDriven> cachedClass = GENERATED_CLASSES.get(identifier);
        if (cachedClass != null) {
            return cachedClass;
        }

        // Extract display name from components or use identifier
        String displayName = extractDisplayName(itemDef);

        // Generate a unique class name
        String className = "net.easecation.bridge.adapter.mot.item.generated.ItemDataDriven_"
            + IdentifierSanitizer.sanitize(identifier);

        try {
            // Use ByteBuddy to generate the class
            @SuppressWarnings("unchecked")
            Class<? extends ItemDataDriven> dynamicClass;

            // For Legacy mode (textureName == null), use two-parameter constructor
            // For Component-based mode, use three-parameter constructor
            if (textureName == null) {
                // Legacy mode: textures are defined client-side in resource pack
                dynamicClass = (Class<? extends ItemDataDriven>) new ByteBuddy()
                    .subclass(ItemDataDriven.class)
                    .name(className)
                    // Define a public no-arg constructor
                    .defineConstructor(Visibility.PUBLIC)
                    // Call super(identifier, displayName) - two-parameter constructor
                    .intercept(MethodCall.invoke(ItemDataDriven.class.getDeclaredConstructor(
                            String.class, String.class))
                        .with(identifier, displayName))
                    .make()
                    .load(ItemDataDriven.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getLoaded();
            } else {
                // Component-based mode: textures are defined server-side
                dynamicClass = (Class<? extends ItemDataDriven>) new ByteBuddy()
                    .subclass(ItemDataDriven.class)
                    .name(className)
                    // Define a public no-arg constructor
                    .defineConstructor(Visibility.PUBLIC)
                    // Call super(identifier, displayName, textureName) - three-parameter constructor
                    .intercept(MethodCall.invoke(ItemDataDriven.class.getDeclaredConstructor(
                            String.class, String.class, String.class))
                        .with(identifier, displayName, textureName))
                    .make()
                    .load(ItemDataDriven.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getLoaded();
            }

            // Cache the generated class
            GENERATED_CLASSES.put(identifier, dynamicClass);

            return dynamicClass;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate dynamic class for item: " + identifier, e);
        }
    }

    /**
     * Extract display name from ItemDef components.
     * Legacy mode items don't have display_name component.
     */
    private static String extractDisplayName(ItemDef itemDef) {
        // Legacy模式没有displayName组件
        if (itemDef.isLegacy()) {
            return null;
        }

        // Component-based模式
        if (itemDef.componentComponents() == null
            || itemDef.componentComponents().minecraft_displayName() == null) {
            return null;
        }

        net.easecation.bridge.core.dto.item.v1_21_60.DisplayName displayName =
            itemDef.componentComponents().minecraft_displayName();
        return displayName.value();
    }

    /**
     * Clear the cache of generated classes.
     * Useful for testing or hot-reloading scenarios.
     */
    public static void clearCache() {
        GENERATED_CLASSES.clear();
    }

    /**
     * Get the number of generated classes.
     */
    public static int getCachedClassCount() {
        return GENERATED_CLASSES.size();
    }
}
