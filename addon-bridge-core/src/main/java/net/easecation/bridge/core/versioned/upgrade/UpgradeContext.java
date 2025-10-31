package net.easecation.bridge.core.versioned.upgrade;

import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.versioned.FormatVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Context object passed through the upgrade chain.
 * Collects warnings and provides utilities for type conversion.
 */
public class UpgradeContext {

    private final FormatVersion sourceVersion;
    private final FormatVersion targetVersion;
    private final List<String> warnings;

    public UpgradeContext(FormatVersion sourceVersion, FormatVersion targetVersion) {
        this.sourceVersion = sourceVersion;
        this.targetVersion = targetVersion;
        this.warnings = new ArrayList<>();
    }

    /**
     * Add a warning message and log it.
     */
    public void addWarning(String message) {
        String fullMessage = String.format("[Upgrade %s→%s] %s", sourceVersion, targetVersion, message);
        warnings.add(message);
        BridgeLoggerHolder.getLogger().warning(fullMessage);
    }

    /**
     * Add a warning for an unmatched field.
     */
    public void addFieldWarning(String fieldName, String reason) {
        addWarning(String.format("Field '%s' could not be upgraded: %s", fieldName, reason));
    }

    /**
     * Add a warning for a type conversion failure.
     */
    public void addTypeConversionWarning(String fieldName, Class<?> fromType, Class<?> toType) {
        addWarning(String.format("Failed to convert field '%s' from %s to %s",
            fieldName, fromType.getSimpleName(), toType.getSimpleName()));
    }

    public FormatVersion getSourceVersion() {
        return sourceVersion;
    }

    public FormatVersion getTargetVersion() {
        return targetVersion;
    }

    public List<String> getWarnings() {
        return Collections.unmodifiableList(warnings);
    }

    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }
}
