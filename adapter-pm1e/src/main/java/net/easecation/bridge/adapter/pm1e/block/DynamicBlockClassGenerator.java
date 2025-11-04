package net.easecation.bridge.adapter.pm1e.block;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.easecation.bridge.core.BlockDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dynamic class generator for BlockDataDriven.
 */
public class DynamicBlockClassGenerator {

    private static final Map<String, Class<? extends BlockDataDriven>> GENERATED_CLASSES = new ConcurrentHashMap<>();

    public static Class<? extends BlockDataDriven> generateBlockClass(BlockDef blockDef, int nukkitId) {
        String identifier = blockDef.id();

        Class<? extends BlockDataDriven> cachedClass = GENERATED_CLASSES.get(identifier);
        if (cachedClass != null) {
            return cachedClass;
        }

        String displayName = extractDisplayName(blockDef);
        String sanitizedName = sanitizeIdentifier(identifier);
        String className = "net.easecation.bridge.adapter.pm1e.block.generated.BlockDataDriven_" + sanitizedName;

        try {
            @SuppressWarnings("unchecked")
            Class<? extends BlockDataDriven> dynamicClass = (Class<? extends BlockDataDriven>) new ByteBuddy()
                .subclass(BlockDataDriven.class)
                .name(className)
                .defineConstructor(Visibility.PUBLIC)
                .intercept(MethodCall.invoke(BlockDataDriven.class.getDeclaredConstructor(
                        String.class, String.class, int.class))
                    .with(identifier, displayName, nukkitId))
                .make()
                .load(BlockDataDriven.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

            GENERATED_CLASSES.put(identifier, dynamicClass);
            return dynamicClass;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate dynamic class for block: " + identifier, e);
        }
    }

    private static String extractDisplayName(BlockDef blockDef) {
        return null;
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
