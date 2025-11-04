package net.easecation.bridge.adapter.pm1e.entity;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.easecation.bridge.core.EntityDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * Dynamic class generator for EntityDataDriven.
 * Generates a unique subclass of EntityDataDriven for each custom entity.
 *
 * <p>This is necessary because PM1E's Entity.registerEntity requires
 * a separate class for each custom entity.</p>
 */
public class DynamicEntityClassGenerator {

    // Cache of generated classes to avoid regeneration
    private static final Map<String, Class<? extends EntityDataDriven>> GENERATED_CLASSES = new ConcurrentHashMap<>();

    /**
     * Generate a unique EntityDataDriven subclass for the given EntityDef.
     *
     * <p>The generated class will:
     * <ul>
     *   <li>Extend EntityDataDriven</li>
     *   <li>Have a unique name based on the entity identifier</li>
     *   <li>Override getEntityIdentifier() to return the fixed identifier</li>
     *   <li>Be cached to avoid regeneration</li>
     * </ul>
     *
     * @param entityDef The EntityDef to generate a class for
     * @return A dynamically generated class extending EntityDataDriven
     */
    public static Class<? extends EntityDataDriven> generateEntityClass(EntityDef entityDef) {
        String identifier = entityDef.id();

        // Validate identifier
        if (identifier == null || identifier.isEmpty() || identifier.equals("unknown:entity")) {
            throw new IllegalArgumentException("EntityDef must have a valid identifier: " + identifier);
        }

        // Check cache first
        Class<? extends EntityDataDriven> cachedClass = GENERATED_CLASSES.get(identifier);
        if (cachedClass != null) {
            return cachedClass;
        }

        // Generate a unique class name
        String sanitizedName = sanitizeIdentifier(identifier);
        String className = "net.easecation.bridge.adapter.pm1e.entity.generated.EntityDataDriven_" + sanitizedName;

        try {
            // Use ByteBuddy to generate the class
            @SuppressWarnings("unchecked")
            Class<? extends EntityDataDriven> dynamicClass = (Class<? extends EntityDataDriven>) new ByteBuddy()
                .subclass(EntityDataDriven.class)
                .name(className)
                // Override getEntityIdentifier() to return the fixed identifier
                .method(named("getEntityIdentifier"))
                .intercept(FixedValue.value(identifier))
                .make()
                .load(EntityDataDriven.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

            // Cache the generated class
            GENERATED_CLASSES.put(identifier, dynamicClass);

            return dynamicClass;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate dynamic class for entity: " + identifier, e);
        }
    }

    /**
     * Sanitize identifier for use as a Java class name.
     */
    private static String sanitizeIdentifier(String identifier) {
        return identifier.replaceAll("[^a-zA-Z0-9_]", "_");
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
