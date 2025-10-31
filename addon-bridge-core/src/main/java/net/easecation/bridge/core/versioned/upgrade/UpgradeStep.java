package net.easecation.bridge.core.versioned.upgrade;

import net.easecation.bridge.core.versioned.FormatVersion;

/**
 * Represents a single version upgrade step (e.g., v1.19.0 -> v1.19.40).
 *
 * @param <F> The source version DTO type
 * @param <T> The target version DTO type
 */
public interface UpgradeStep<F, T> {

    /**
     * Get the source version of this upgrade step.
     */
    FormatVersion fromVersion();

    /**
     * Get the target version of this upgrade step.
     */
    FormatVersion toVersion();

    /**
     * Perform the upgrade from source to target version.
     *
     * @param oldObject The source version object to upgrade
     * @param context The upgrade context for collecting warnings
     * @return The upgraded target version object
     * @throws UpgradeException if upgrade fails critically
     */
    T upgrade(F oldObject, UpgradeContext context) throws UpgradeException;
}
