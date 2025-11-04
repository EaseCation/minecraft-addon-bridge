package net.easecation.bridge.adapter.pm1e.item;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.easecation.bridge.core.ItemDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dynamic class generator for ItemDataDriven.
 * Generates a unique subclass of ItemDataDriven for each custom item.
 */
public class DynamicItemClassGenerator {

    private static final Map<String, Class<? extends ItemDataDriven>> GENERATED_CLASSES = new ConcurrentHashMap<>();

    public static Class<? extends ItemDataDriven> generateItemClass(ItemDef itemDef, int nukkitId) {
        String identifier = itemDef.id();

        Class<? extends ItemDataDriven> cachedClass = GENERATED_CLASSES.get(identifier);
        if (cachedClass != null) {
            return cachedClass;
        }

        String displayName = extractDisplayName(itemDef);
        String sanitizedName = sanitizeIdentifier(identifier);
        String className = "net.easecation.bridge.adapter.pm1e.item.generated.ItemDataDriven_" + sanitizedName;

        try {
            @SuppressWarnings("unchecked")
            Class<? extends ItemDataDriven> dynamicClass = (Class<? extends ItemDataDriven>) new ByteBuddy()
                .subclass(ItemDataDriven.class)
                .name(className)
                .defineConstructor(Visibility.PUBLIC)
                .intercept(MethodCall.invoke(ItemDataDriven.class.getDeclaredConstructor(
                        int.class, String.class, String.class))
                    .with(nukkitId, identifier, displayName))
                .defineConstructor(Visibility.PUBLIC)
                    .withParameters(Integer.class)
                .intercept(MethodCall.invoke(ItemDataDriven.class.getDeclaredConstructor(
                        int.class, String.class, String.class))
                    .with(nukkitId, identifier, displayName))
                .defineConstructor(Visibility.PUBLIC)
                    .withParameters(Integer.class, int.class)
                .intercept(MethodCall.invoke(ItemDataDriven.class.getDeclaredConstructor(
                        int.class, String.class, String.class))
                    .with(nukkitId, identifier, displayName))
                .make()
                .load(ItemDataDriven.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

            GENERATED_CLASSES.put(identifier, dynamicClass);
            return dynamicClass;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate dynamic class for item: " + identifier, e);
        }
    }

    private static String extractDisplayName(ItemDef itemDef) {
        if (itemDef.isLegacy()) {
            return null;
        }

        if (itemDef.componentComponents() == null
            || itemDef.componentComponents().minecraft_displayName() == null) {
            return null;
        }

        net.easecation.bridge.core.dto.item.v1_21_60.DisplayName displayName =
            itemDef.componentComponents().minecraft_displayName();
        return displayName.value();
    }

    private static String sanitizeIdentifier(String identifier) {
        return identifier.replaceAll("[^a-zA-Z0-9_]", "_");
    }

    public static void clearCache() {
        GENERATED_CLASSES.clear();
    }

    public static int getCachedClassCount() {
        return GENERATED_CLASSES.size();
    }
}
