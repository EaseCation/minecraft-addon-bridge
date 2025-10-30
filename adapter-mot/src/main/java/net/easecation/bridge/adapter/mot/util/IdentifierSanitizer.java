package net.easecation.bridge.adapter.mot.util;

/**
 * Utility class for sanitizing identifiers to create valid Java class names.
 */
public class IdentifierSanitizer {

    /**
     * Sanitize an identifier to create a valid Java class name.
     *
     * <p>Examples:
     * <ul>
     *   <li>"namespace:item_name" -> "Namespace_ItemName_123456"</li>
     *   <li>"my-addon:cool-item" -> "MyAddon_CoolItem_789012"</li>
     * </ul>
     *
     * @param identifier The identifier to sanitize (e.g., "namespace:item_name")
     * @return A valid Java class name with hash suffix for uniqueness
     */
    public static String sanitize(String identifier) {
        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Identifier cannot be null or empty");
        }

        // Step 1: Split by ':' to separate namespace and name
        String[] parts = identifier.split(":", 2);
        String namespace = parts.length > 0 ? parts[0] : "";
        String name = parts.length > 1 ? parts[1] : parts[0];

        // Step 2: Convert to PascalCase
        String sanitizedNamespace = toPascalCase(namespace);
        String sanitizedName = toPascalCase(name);

        // Step 3: Combine with underscore
        String combined = sanitizedNamespace.isEmpty()
            ? sanitizedName
            : sanitizedNamespace + "_" + sanitizedName;

        // Step 4: Add hash for uniqueness (防止不同identifier经过转换后重名)
        int hash = Math.abs(identifier.hashCode());

        return combined + "_" + hash;
    }

    /**
     * Convert a string to PascalCase.
     *
     * <p>Examples:
     * <ul>
     *   <li>"item_name" -> "ItemName"</li>
     *   <li>"cool-item" -> "CoolItem"</li>
     *   <li>"namespace" -> "Namespace"</li>
     * </ul>
     */
    private static String toPascalCase(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : input.toCharArray()) {
            if (c == '_' || c == '-' || c == '.' || c == ' ') {
                // Skip separator and capitalize next character
                capitalizeNext = true;
            } else if (Character.isLetterOrDigit(c)) {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
            // Skip other special characters
        }

        return result.toString();
    }

    /**
     * Generate a simple sanitized name without PascalCase conversion.
     * Just replaces invalid characters with underscores.
     *
     * @param identifier The identifier to sanitize
     * @return A sanitized identifier suitable for class names
     */
    public static String sanitizeSimple(String identifier) {
        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Identifier cannot be null or empty");
        }

        // Replace invalid characters with underscores
        String sanitized = identifier.replaceAll("[^a-zA-Z0-9_]", "_");

        // Add hash for uniqueness
        int hash = Math.abs(identifier.hashCode());

        return sanitized + "_" + hash;
    }
}
