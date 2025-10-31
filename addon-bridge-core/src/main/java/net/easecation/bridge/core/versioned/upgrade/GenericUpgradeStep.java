package net.easecation.bridge.core.versioned.upgrade;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.versioned.FormatVersion;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Generic upgrade step that uses reflection to automatically copy fields
 * from source to target version DTOs. Handles 70-80% of upgrade scenarios
 * where only new fields are added.
 *
 * Works with Java Record types by inspecting record components and using
 * the canonical constructor.
 *
 * @param <F> Source version DTO type (must be a Record)
 * @param <T> Target version DTO type (must be a Record)
 */
public class GenericUpgradeStep<F extends Record, T extends Record> implements UpgradeStep<F, T> {

    private final FormatVersion fromVersion;
    private final FormatVersion toVersion;
    private final Class<F> fromClass;
    private final Class<T> toClass;
    private final ObjectMapper mapper;

    // Cached reflection data
    private final Map<String, Method> fromAccessors;
    private final RecordComponent[] toComponents;
    private final Constructor<T> toConstructor;

    public GenericUpgradeStep(
        FormatVersion fromVersion,
        FormatVersion toVersion,
        Class<F> fromClass,
        Class<T> toClass,
        ObjectMapper mapper
    ) {
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
        this.fromClass = fromClass;
        this.toClass = toClass;
        this.mapper = mapper;

        // Pre-compute reflection data for performance
        this.fromAccessors = buildAccessorMap(fromClass);
        this.toComponents = toClass.getRecordComponents();
        this.toConstructor = findCanonicalConstructor(toClass, toComponents);
    }

    @Override
    public FormatVersion fromVersion() {
        return fromVersion;
    }

    @Override
    public FormatVersion toVersion() {
        return toVersion;
    }

    @Override
    public T upgrade(F oldObject, UpgradeContext context) throws UpgradeException {
        try {
            // Build constructor arguments for target record
            Object[] args = new Object[toComponents.length];

            for (int i = 0; i < toComponents.length; i++) {
                RecordComponent targetComponent = toComponents[i];
                String fieldName = targetComponent.getName();
                Class<?> targetType = targetComponent.getType();

                // Try to find matching field in source
                Method sourceAccessor = fromAccessors.get(fieldName);

                if (sourceAccessor == null) {
                    // New field in target version - set to null
                    args[i] = null;
                    continue;
                }

                try {
                    Object sourceValue = sourceAccessor.invoke(oldObject);
                    Class<?> sourceType = sourceAccessor.getReturnType();

                    // Handle null values
                    if (sourceValue == null) {
                        args[i] = null;
                        continue;
                    }

                    // Priority 1: Map/Collection - ALWAYS use JSON deep conversion
                    // This ensures nested objects (like sealed interface variants) are recreated with target version
                    if (sourceValue instanceof Map || sourceValue instanceof Collection) {
                        try {
                            String json = mapper.writeValueAsString(sourceValue);
                            JavaType targetJavaType = mapper.getTypeFactory().constructType(targetComponent.getGenericType());
                            args[i] = mapper.readValue(json, targetJavaType);
                            BridgeLoggerHolder.getLogger().debug(String.format(
                                "[%s->%s] Field '%s': JSON converted %s",
                                fromVersion, toVersion, fieldName, sourceType.getSimpleName()));
                            continue;
                        } catch (Exception e) {
                            // Fail immediately - no silent failures
                            throw new UpgradeException(fromVersion, toVersion,
                                String.format("Failed to JSON-convert %s field '%s': %s\n" +
                                    "This requires manual upgrade logic.",
                                    sourceType.getSimpleName(), fieldName, e.getMessage()), e);
                        }
                    }

                    // Priority 2: Direct type assignment if types are compatible
                    if (targetType.isAssignableFrom(sourceType)) {
                        args[i] = sourceValue;
                        BridgeLoggerHolder.getLogger().debug(String.format(
                            "[%s->%s] Field '%s': Direct assignment",
                            fromVersion, toVersion, fieldName));
                        continue;
                    }

                    // Priority 3: Nested Records with same simple name - recursively upgrade
                    if (sourceValue instanceof Record && Record.class.isAssignableFrom(targetType)) {
                        String sourceSimpleName = sourceType.getSimpleName();
                        String targetSimpleName = targetType.getSimpleName();

                        if (sourceSimpleName.equals(targetSimpleName)) {
                            try {
                                args[i] = upgradeNestedRecord(
                                    (Record) sourceValue,
                                    sourceType,
                                    targetType,
                                    context
                                );
                                BridgeLoggerHolder.getLogger().debug(String.format(
                                    "[%s->%s] Field '%s': Upgraded nested Record %s",
                                    fromVersion, toVersion, fieldName, targetSimpleName));
                                continue;
                            } catch (Exception e) {
                                // Fail immediately - no silent failures
                                throw new UpgradeException(fromVersion, toVersion,
                                    String.format("Failed to upgrade nested Record '%s' (field '%s'): %s",
                                        targetSimpleName, fieldName, e.getMessage()), e);
                            }
                        }
                    }

                    // Priority 4: Regular classes with same simple name - recursively upgrade
                    if (!(sourceValue instanceof Record) && !targetType.isRecord()) {
                        String sourceSimpleName = sourceType.getSimpleName();
                        String targetSimpleName = targetType.getSimpleName();

                        if (sourceSimpleName.equals(targetSimpleName)) {
                            try {
                                args[i] = upgradeNestedClass(
                                    sourceValue,
                                    sourceType,
                                    targetType,
                                    context
                                );
                                BridgeLoggerHolder.getLogger().debug(String.format(
                                    "[%s->%s] Field '%s': Upgraded nested class %s",
                                    fromVersion, toVersion, fieldName, targetSimpleName));
                                continue;
                            } catch (Exception e) {
                                // Fail immediately - no silent failures
                                throw new UpgradeException(fromVersion, toVersion,
                                    String.format("Failed to upgrade nested class '%s' (field '%s'): %s",
                                        targetSimpleName, fieldName, e.getMessage()), e);
                            }
                        }
                    }

                    // Priority 5: JSON Fallback for same-name types (sealed interfaces, enums, etc.)
                    // This handles types that are structurally identical but from different version packages
                    String sourceSimpleName = sourceType.getSimpleName();
                    String targetSimpleName = targetType.getSimpleName();

                    if (sourceSimpleName.equals(targetSimpleName)) {
                        try {
                            // Use JSON serialization/deserialization to convert cross-version types
                            // Works for: sealed interfaces (e.g., Range_a_B), enums, complex nested structures
                            String json = mapper.writeValueAsString(sourceValue);
                            args[i] = mapper.readValue(json, targetType);
                            BridgeLoggerHolder.getLogger().debug(String.format(
                                "[%s->%s] Field '%s': JSON fallback for %s (structurally identical type)",
                                fromVersion, toVersion, fieldName, targetSimpleName));
                            continue;
                        } catch (Exception e) {
                            // JSON fallback failed - now we really need manual upgrade logic
                            throw new UpgradeException(fromVersion, toVersion,
                                String.format("JSON fallback failed for field '%s': %s -> %s\n" +
                                    "Types have same name but JSON conversion failed (requires manual upgrade logic)\n" +
                                    "Error: %s",
                                    fieldName, sourceType.getName(), targetType.getName(), e.getMessage()), e);
                        }
                    }

                    // Type mismatch - fail immediately
                    throw new UpgradeException(fromVersion, toVersion,
                        String.format("Type mismatch for field '%s': %s -> %s (requires manual upgrade logic)",
                            fieldName, sourceType.getName(), targetType.getName()));

                } catch (UpgradeException e) {
                    // Re-throw UpgradeException as-is
                    throw e;
                } catch (Exception e) {
                    // Failed to access source field - fail immediately
                    throw new UpgradeException(fromVersion, toVersion,
                        String.format("Failed to access field '%s': %s", fieldName, e.getMessage()), e);
                }
            }

            // Construct target record using canonical constructor
            try {
                return toConstructor.newInstance(args);
            } catch (Exception e) {
                // Constructor invocation failed - provide detailed error
                StringBuilder errorDetails = new StringBuilder();
                errorDetails.append(String.format("Failed to construct %s with %d arguments:\n",
                    toClass.getSimpleName(), args.length));

                for (int i = 0; i < toComponents.length && i < args.length; i++) {
                    RecordComponent component = toComponents[i];
                    Object arg = args[i];

                    String expectedTypeName = component.getType().getName();
                    String actualTypeName = arg == null ? "null" : arg.getClass().getName();

                    errorDetails.append(String.format("  [%d] %s:\n",
                        i, component.getName()));
                    errorDetails.append(String.format("      Expected: %s\n", expectedTypeName));
                    errorDetails.append(String.format("      Actual:   %s\n", actualTypeName));

                    // Check if types match
                    if (arg != null && !component.getType().isAssignableFrom(arg.getClass())) {
                        errorDetails.append(String.format("      ❌ Type mismatch!\n"));
                    }
                }

                // Get the root cause
                Throwable rootCause = e;
                while (rootCause.getCause() != null) {
                    rootCause = rootCause.getCause();
                }

                errorDetails.append(String.format("\nRoot cause: %s: %s\n",
                    rootCause.getClass().getSimpleName(),
                    rootCause.getMessage()));

                throw new UpgradeException(fromVersion, toVersion,
                    String.format("Constructor invocation failed for %s -> %s:\n%s",
                        fromClass.getSimpleName(), toClass.getSimpleName(), errorDetails.toString()), e);
            }

        } catch (UpgradeException e) {
            // Re-throw UpgradeException as-is
            throw e;
        } catch (Exception e) {
            throw new UpgradeException(fromVersion, toVersion,
                String.format("Reflection failed for %s -> %s: %s",
                    fromClass.getSimpleName(), toClass.getSimpleName(), e.getMessage()), e);
        }
    }

    /**
     * Recursively upgrade a nested Record from source type to target type.
     * This is needed when a Record contains other Records that also need to be upgraded.
     */
    @SuppressWarnings("unchecked")
    private Record upgradeNestedRecord(
        Record sourceRecord,
        Class<?> sourceClass,
        Class<?> targetClass,
        UpgradeContext context
    ) throws Exception {
        // Build accessor map for source
        Map<String, Method> sourceAccessors = buildAccessorMap((Class<? extends Record>) sourceClass);

        // Get target record components
        RecordComponent[] targetComponents = targetClass.getRecordComponents();

        // Find canonical constructor for target
        Constructor<?> targetConstructor = findCanonicalConstructor((Class<? extends Record>) targetClass, targetComponents);

        // Build constructor arguments
        Object[] args = new Object[targetComponents.length];

        for (int i = 0; i < targetComponents.length; i++) {
            RecordComponent targetComponent = targetComponents[i];
            String fieldName = targetComponent.getName();
            Class<?> targetType = targetComponent.getType();

            Method sourceAccessor = sourceAccessors.get(fieldName);

            if (sourceAccessor == null) {
                // New field in target - set to null
                args[i] = null;
                continue;
            }

            Object sourceValue = sourceAccessor.invoke(sourceRecord);

            if (sourceValue == null) {
                args[i] = null;
                continue;
            }

            Class<?> sourceType = sourceAccessor.getReturnType();

            // Priority 1: Map/Collection - ALWAYS use JSON deep conversion
            if (sourceValue instanceof Map || sourceValue instanceof Collection) {
                try {
                    String json = mapper.writeValueAsString(sourceValue);
                    JavaType targetJavaType = mapper.getTypeFactory().constructType(targetComponent.getGenericType());
                    args[i] = mapper.readValue(json, targetJavaType);
                    continue;
                } catch (Exception e) {
                    // Fail immediately - no silent failures
                    throw new RuntimeException(String.format(
                        "Failed to JSON-convert %s field '%s' in nested Record: %s",
                        sourceType.getSimpleName(), fieldName, e.getMessage()), e);
                }
            }

            // Priority 2: Direct assignment if types match
            if (targetType.isAssignableFrom(sourceType)) {
                args[i] = sourceValue;
                continue;
            }

            // Priority 3: Recursively upgrade nested Records
            if (sourceValue instanceof Record && Record.class.isAssignableFrom(targetType)) {
                String sourceSimpleName = sourceType.getSimpleName();
                String targetSimpleName = targetType.getSimpleName();

                if (sourceSimpleName.equals(targetSimpleName)) {
                    args[i] = upgradeNestedRecord(
                        (Record) sourceValue,
                        sourceType,
                        targetType,
                        context
                    );
                    continue;
                }
            }

            // Priority 4: Recursively upgrade nested regular classes
            if (!(sourceValue instanceof Record) && !targetType.isRecord()) {
                String sourceSimpleName = sourceType.getSimpleName();
                String targetSimpleName = targetType.getSimpleName();

                if (sourceSimpleName.equals(targetSimpleName)) {
                    args[i] = upgradeNestedClass(
                        sourceValue,
                        sourceType,
                        targetType,
                        context
                    );
                    continue;
                }
            }

            // Priority 5: JSON Fallback for same-name types (sealed interfaces, enums, etc.)
            String sourceSimpleName = sourceType.getSimpleName();
            String targetSimpleName = targetType.getSimpleName();

            if (sourceSimpleName.equals(targetSimpleName)) {
                try {
                    // Use JSON serialization/deserialization to convert cross-version types
                    String json = mapper.writeValueAsString(sourceValue);
                    args[i] = mapper.readValue(json, targetType);
                    continue;
                } catch (Exception e) {
                    // JSON fallback failed - throw detailed error
                    throw new RuntimeException(String.format(
                        "JSON fallback failed for field '%s' in nested Record: %s -> %s\n" +
                        "Types have same name but JSON conversion failed\n" +
                        "Error: %s",
                        fieldName, sourceType.getName(), targetType.getName(), e.getMessage()), e);
                }
            }

            // Type mismatch - fail immediately
            throw new RuntimeException(String.format(
                "Type mismatch for field '%s' in nested Record: %s -> %s (requires manual upgrade logic)",
                fieldName, sourceType.getName(), targetType.getName()));
        }

        return (Record) targetConstructor.newInstance(args);
    }

    /**
     * Recursively upgrade a nested regular class (non-Record) from source type to target type.
     * This is needed for large DTO classes like Components that have too many fields to be Records.
     *
     * @param sourceObject The source object to upgrade
     * @param sourceClass The source class type
     * @param targetClass The target class type
     * @param context The upgrade context
     * @return The upgraded object of target type
     * @throws Exception if upgrade fails
     */
    @SuppressWarnings("unchecked")
    private Object upgradeNestedClass(
        Object sourceObject,
        Class<?> sourceClass,
        Class<?> targetClass,
        UpgradeContext context
    ) throws Exception {
        // Create new instance of target class using no-arg constructor
        Constructor<?> targetConstructor = targetClass.getDeclaredConstructor();
        targetConstructor.setAccessible(true);
        Object targetObject = targetConstructor.newInstance();

        // Get all fields from source class
        Map<String, Field> sourceFields = new HashMap<>();
        for (Field field : sourceClass.getDeclaredFields()) {
            field.setAccessible(true);
            sourceFields.put(field.getName(), field);
        }

        // Copy fields from source to target
        for (Field targetField : targetClass.getDeclaredFields()) {
            targetField.setAccessible(true);
            String fieldName = targetField.getName();
            Class<?> targetType = targetField.getType();

            Field sourceField = sourceFields.get(fieldName);
            if (sourceField == null) {
                // New field in target - leave as default value
                continue;
            }

            Object sourceValue = sourceField.get(sourceObject);
            if (sourceValue == null) {
                targetField.set(targetObject, null);
                continue;
            }

            Class<?> sourceType = sourceField.getType();

            // Priority 1: Map/Collection - ALWAYS use JSON deep conversion
            if (sourceValue instanceof Map || sourceValue instanceof Collection) {
                try {
                    String json = mapper.writeValueAsString(sourceValue);
                    JavaType targetJavaType = mapper.getTypeFactory().constructType(targetField.getGenericType());
                    Object upgradedValue = mapper.readValue(json, targetJavaType);
                    targetField.set(targetObject, upgradedValue);
                    continue;
                } catch (Exception e) {
                    // Fail immediately - no silent failures
                    throw new RuntimeException(String.format(
                        "Failed to JSON-convert %s field '%s' in nested class: %s",
                        sourceType.getSimpleName(), fieldName, e.getMessage()), e);
                }
            }

            // Priority 2: Direct assignment if types match
            if (targetType.isAssignableFrom(sourceType)) {
                targetField.set(targetObject, sourceValue);
                continue;
            }

            // Priority 3: Recursively upgrade nested Records
            if (sourceValue instanceof Record && Record.class.isAssignableFrom(targetType)) {
                String sourceSimpleName = sourceType.getSimpleName();
                String targetSimpleName = targetType.getSimpleName();

                if (sourceSimpleName.equals(targetSimpleName)) {
                    Object upgradedValue = upgradeNestedRecord(
                        (Record) sourceValue,
                        sourceType,
                        targetType,
                        context
                    );
                    targetField.set(targetObject, upgradedValue);
                    continue;
                }
            }

            // Priority 4: Recursively upgrade nested regular classes
            if (!(sourceValue instanceof Record) && !targetType.isRecord()) {
                String sourceSimpleName = sourceType.getSimpleName();
                String targetSimpleName = targetType.getSimpleName();

                if (sourceSimpleName.equals(targetSimpleName)) {
                    Object upgradedValue = upgradeNestedClass(
                        sourceValue,
                        sourceType,
                        targetType,
                        context
                    );
                    targetField.set(targetObject, upgradedValue);
                    continue;
                }
            }

            // Priority 5: JSON Fallback for same-name types (sealed interfaces, enums, etc.)
            String sourceSimpleName = sourceType.getSimpleName();
            String targetSimpleName = targetType.getSimpleName();

            if (sourceSimpleName.equals(targetSimpleName)) {
                try {
                    // Use JSON serialization/deserialization to convert cross-version types
                    String json = mapper.writeValueAsString(sourceValue);
                    Object upgradedValue = mapper.readValue(json, targetType);
                    targetField.set(targetObject, upgradedValue);
                    continue;
                } catch (Exception e) {
                    // JSON fallback failed - throw detailed error
                    throw new RuntimeException(String.format(
                        "JSON fallback failed for field '%s' in nested class: %s -> %s\n" +
                        "Types have same name but JSON conversion failed\n" +
                        "Error: %s",
                        fieldName, sourceType.getName(), targetType.getName(), e.getMessage()), e);
                }
            }

            // Type mismatch - fail immediately
            throw new RuntimeException(String.format(
                "Type mismatch for field '%s' in nested class: %s -> %s (requires manual upgrade logic)",
                fieldName, sourceType.getName(), targetType.getName()));
        }

        return targetObject;
    }

    /**
     * Build a map of field name to accessor method for a Record class.
     */
    private static Map<String, Method> buildAccessorMap(Class<? extends Record> recordClass) {
        Map<String, Method> accessors = new HashMap<>();
        RecordComponent[] components = recordClass.getRecordComponents();

        for (RecordComponent component : components) {
            String name = component.getName();
            Method accessor = component.getAccessor();
            accessors.put(name, accessor);
        }

        return accessors;
    }

    /**
     * Check if two types have generic parameters that need upgrade (cross-version).
     * Returns true if the generic parameters contain different version packages.
     *
     * @param sourceType The source generic type
     * @param targetType The target generic type
     * @return true if generic parameters need upgrade, false otherwise
     */
    private boolean needsGenericTypeUpgrade(Type sourceType, Type targetType) {
        // Both must be parameterized types (have generic parameters)
        if (!(sourceType instanceof ParameterizedType) || !(targetType instanceof ParameterizedType)) {
            return false;
        }

        ParameterizedType sourceParam = (ParameterizedType) sourceType;
        ParameterizedType targetParam = (ParameterizedType) targetType;

        Type[] sourceArgs = sourceParam.getActualTypeArguments();
        Type[] targetArgs = targetParam.getActualTypeArguments();

        // Must have same number of type arguments
        if (sourceArgs.length != targetArgs.length) {
            return false;
        }

        // Check each type argument pair
        for (int i = 0; i < sourceArgs.length; i++) {
            String sourceTypeName = sourceArgs[i].getTypeName();
            String targetTypeName = targetArgs[i].getTypeName();

            // Check if they are from different version packages
            // Example: "net.easecation.bridge.core.dto.block.v1_19_50.MaterialInstancesValue"
            //      vs  "net.easecation.bridge.core.dto.block.v1_21_60.MaterialInstancesValue"
            if (isVersionedType(sourceTypeName) && isVersionedType(targetTypeName)) {
                String sourceVersion = extractVersion(sourceTypeName);
                String targetVersion = extractVersion(targetTypeName);

                if (sourceVersion != null && targetVersion != null && !sourceVersion.equals(targetVersion)) {
                    return true; // Different versions, needs upgrade
                }
            }
        }

        return false;
    }

    /**
     * Check if a type name contains a version package (e.g., "v1_19_50", "v1_21_60").
     */
    private boolean isVersionedType(String typeName) {
        return typeName.matches(".*\\.v\\d+_\\d+_\\d+\\..*");
    }

    /**
     * Extract version string from a fully qualified type name.
     * Example: "net.easecation.bridge.core.dto.block.v1_19_50.MaterialInstancesValue" -> "v1_19_50"
     */
    private String extractVersion(String typeName) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\.(v\\d+_\\d+_\\d+)\\.");
        java.util.regex.Matcher matcher = pattern.matcher(typeName);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Find the canonical constructor for a Record class.
     * The canonical constructor has parameters matching all record components in order.
     */
    @SuppressWarnings("unchecked")
    private static <T extends Record> Constructor<T> findCanonicalConstructor(
        Class<T> recordClass,
        RecordComponent[] components
    ) {
        Class<?>[] paramTypes = Arrays.stream(components)
            .map(RecordComponent::getType)
            .toArray(Class<?>[]::new);

        try {
            return recordClass.getDeclaredConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                "Could not find canonical constructor for " + recordClass.getName(), e);
        }
    }

    /**
     * Check if a value can be assigned to a target type.
     * Returns true if value is null or if value's type is assignable to target type.
     * For Record types or regular classes with the same simple name, assume compatibility (for version upgrades).
     */
    private static boolean isAssignableOrNull(Object value, Class<?> targetType) {
        if (value == null) {
            return true; // null can be assigned to any type
        }

        // Direct type check (same class or inheritance)
        if (targetType.isAssignableFrom(value.getClass())) {
            return true;
        }

        // Special handling for Record types in version upgrades:
        // If both are Records and have the same simple class name,
        // assume they are structurally compatible (e.g., v1_19_0.Description → v1_19_40.Description)
        if (value instanceof Record && Record.class.isAssignableFrom(targetType)) {
            String valueSimpleName = value.getClass().getSimpleName();
            String targetSimpleName = targetType.getSimpleName();

            if (valueSimpleName.equals(targetSimpleName)) {
                // Same record name across versions - assume compatible
                return true;
            }
        }

        // Special handling for regular classes (non-Record) in version upgrades:
        // If both have the same simple class name, assume they are structurally compatible
        // (e.g., v1_19_0.Components → v1_19_40.Components)
        // This is needed for large DTO classes like Components that aren't Records
        String valueSimpleName = value.getClass().getSimpleName();
        String targetSimpleName = targetType.getSimpleName();
        if (valueSimpleName.equals(targetSimpleName)) {
            // Same class name across versions - assume compatible
            return true;
        }

        return false;
    }
}
