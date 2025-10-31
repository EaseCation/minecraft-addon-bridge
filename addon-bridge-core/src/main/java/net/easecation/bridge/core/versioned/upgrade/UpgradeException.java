package net.easecation.bridge.core.versioned.upgrade;

import net.easecation.bridge.core.versioned.FormatVersion;

/**
 * Exception thrown when version upgrade fails.
 */
public class UpgradeException extends Exception {

    private final FormatVersion fromVersion;
    private final FormatVersion toVersion;

    public UpgradeException(FormatVersion fromVersion, FormatVersion toVersion, String message) {
        super(String.format("Failed to upgrade from %s to %s: %s", fromVersion, toVersion, message));
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
    }

    public UpgradeException(FormatVersion fromVersion, FormatVersion toVersion, String message, Throwable cause) {
        super(String.format("Failed to upgrade from %s to %s: %s", fromVersion, toVersion, message), cause);
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
    }

    public FormatVersion getFromVersion() {
        return fromVersion;
    }

    public FormatVersion getToVersion() {
        return toVersion;
    }
}
