package net.easecation.bridge.adapter.mot.block;

import cn.nukkit.block.custom.properties.BlockProperties;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.easecation.bridge.adapter.mot.util.IdentifierSanitizer;
import net.easecation.bridge.core.BlockDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dynamic class generator for BlockDataDriven.
 * Generates a unique subclass of BlockDataDriven for each custom block.
 *
 * <p>This is necessary because MOT's CustomBlockManager requires
 * a factory that creates instances, and we need unique classes for each block type.</p>
 */
public class DynamicBlockClassGenerator {

    // Cache of generated classes to avoid regeneration
    private static final Map<String, Class<? extends BlockDataDriven>> GENERATED_CLASSES = new ConcurrentHashMap<>();
    private static final Map<String, Class<? extends BlockDataDriven.WithProperties>> GENERATED_PROPERTY_CLASSES = new ConcurrentHashMap<>();

    /**
     * Generate a unique BlockDataDriven subclass for the given BlockDef.
     *
     * <p>The generated class will:
     * <ul>
     *   <li>Extend BlockDataDriven or BlockDataDriven.WithProperties (if properties exist)</li>
     *   <li>Have a unique name based on the block identifier</li>
     *   <li>Call super constructor with identifier and nukkitId</li>
     *   <li>Be cached to avoid regeneration</li>
     * </ul>
     *
     * @param blockDef The BlockDef to generate a class for
     * @param nukkitId The Nukkit block ID
     * @param properties The BlockProperties (can be null)
     * @return A dynamically generated class extending BlockDataDriven
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends BlockDataDriven> generateBlockClass(BlockDef blockDef, int nukkitId, BlockProperties properties) {
        String identifier = blockDef.id();

        // Choose between simple block and property block
        if (properties != null) {
            // Cast is safe since WithProperties extends CustomBlockMeta which we treat as BlockDataDriven-compatible
            Class<? extends BlockDataDriven.WithProperties> propClass = generatePropertyBlockClass(identifier, nukkitId, properties);
            return (Class<? extends BlockDataDriven>) (Class<?>) propClass;
        } else {
            return generateSimpleBlockClass(identifier, nukkitId);
        }
    }

    /**
     * Generate a simple block class (no properties).
     */
    private static Class<? extends BlockDataDriven> generateSimpleBlockClass(String identifier, int nukkitId) {
        // Check cache first
        Class<? extends BlockDataDriven> cachedClass = GENERATED_CLASSES.get(identifier);
        if (cachedClass != null) {
            return cachedClass;
        }

        // Generate a unique class name
        String className = "net.easecation.bridge.adapter.mot.block.generated.BlockDataDriven_"
            + IdentifierSanitizer.sanitize(identifier);

        try {
            // Use ByteBuddy to generate the class
            Class<?> rawClass = new ByteBuddy()
                .subclass(BlockDataDriven.class)
                .name(className)
                // Define a public no-arg constructor
                .defineConstructor(Visibility.PUBLIC)
                // Call super(identifier, nukkitId)
                .intercept(MethodCall.invoke(BlockDataDriven.class.getDeclaredConstructor(
                        String.class, int.class))
                    .with(identifier, nukkitId))
                .make()
                .load(BlockDataDriven.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

            @SuppressWarnings("unchecked")
            Class<? extends BlockDataDriven> dynamicClass = (Class<? extends BlockDataDriven>) rawClass;

            // Cache the generated class
            GENERATED_CLASSES.put(identifier, dynamicClass);

            return dynamicClass;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate dynamic class for block: " + identifier, e);
        }
    }

    /**
     * Generate a property block class (with BlockProperties).
     * The generated class will have a constructor that accepts an int meta parameter.
     */
    private static Class<? extends BlockDataDriven.WithProperties> generatePropertyBlockClass(
            String identifier, int nukkitId, BlockProperties properties) {
        // Check cache first
        Class<? extends BlockDataDriven.WithProperties> cachedClass = GENERATED_PROPERTY_CLASSES.get(identifier);
        if (cachedClass != null) {
            return cachedClass;
        }

        // Generate a unique class name
        String className = "net.easecation.bridge.adapter.mot.block.generated.BlockDataDrivenWithProperties_"
            + IdentifierSanitizer.sanitize(identifier);

        try {
            // Use ByteBuddy to generate the class with a constructor that accepts int meta
            Class<?> rawClass = new ByteBuddy()
                .subclass(BlockDataDriven.WithProperties.class)
                .name(className)
                // Define a public constructor that accepts int meta parameter
                .defineConstructor(Visibility.PUBLIC)
                .withParameters(int.class)
                // Call super(identifier, nukkitId, properties, meta)
                // meta comes from the first (and only) parameter at index 0
                .intercept(MethodCall.invoke(BlockDataDriven.WithProperties.class.getDeclaredConstructor(
                        String.class, int.class, BlockProperties.class, int.class))
                    .with(identifier, nukkitId, properties)
                    .withArgument(0))  // Pass the meta parameter from constructor argument
                .make()
                .load(BlockDataDriven.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

            @SuppressWarnings("unchecked")
            Class<? extends BlockDataDriven.WithProperties> dynamicClass =
                (Class<? extends BlockDataDriven.WithProperties>) rawClass;

            // Cache the generated class
            GENERATED_PROPERTY_CLASSES.put(identifier, dynamicClass);

            return dynamicClass;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate dynamic property class for block: " + identifier, e);
        }
    }

    /**
     * Clear the cache of generated classes.
     * Useful for testing or hot-reloading scenarios.
     */
    public static void clearCache() {
        GENERATED_CLASSES.clear();
        GENERATED_PROPERTY_CLASSES.clear();
    }

    /**
     * Get the number of generated classes.
     */
    public static int getCachedClassCount() {
        return GENERATED_CLASSES.size() + GENERATED_PROPERTY_CLASSES.size();
    }
}
