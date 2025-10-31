package net.easecation.bridge.core.versioned.upgrade;

import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.versioned.FormatVersion;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Central engine for managing and executing version upgrades.
 * Maintains a registry of upgrade steps and builds upgrade chains.
 *
 * @param <T> The type being upgraded (e.g., Entity, Item, Block)
 */
public class VersionUpgrader<T> {

    private final Map<FormatVersion, UpgradeStep<?, ?>> upgradeSteps = new LinkedHashMap<>();
    private final FormatVersion targetVersion;

    public VersionUpgrader(FormatVersion targetVersion) {
        this.targetVersion = targetVersion;
    }

    /**
     * Register an upgrade step. Steps must be registered in order from oldest to newest.
     */
    public <F, U> void registerStep(UpgradeStep<F, U> step) {
        if (!step.toVersion().equals(targetVersion)) {
            // Only add steps that lead towards the target
            if (step.toVersion().compareTo(targetVersion) > 0) {
                throw new IllegalArgumentException(
                    String.format("Cannot register step to version %s beyond target %s",
                        step.toVersion(), targetVersion));
            }
        }
        upgradeSteps.put(step.fromVersion(), step);
        BridgeLoggerHolder.getLogger().debug(String.format("Registered upgrade step: %s -> %s", step.fromVersion(), step.toVersion()));
    }

    /**
     * Upgrade an object from its current version to the target version.
     *
     * @param object The object to upgrade
     * @param currentVersion The current version of the object
     * @return The upgraded object at target version
     * @throws UpgradeException if upgrade fails
     */
    @SuppressWarnings("unchecked")
    public T upgrade(Object object, FormatVersion currentVersion) throws UpgradeException {
        if (currentVersion.equals(targetVersion)) {
            return (T) object;
        }

        if (currentVersion.compareTo(targetVersion) > 0) {
            throw new UpgradeException(currentVersion, targetVersion,
                "Cannot downgrade from newer version to older version");
        }

        BridgeLoggerHolder.getLogger().info(String.format("Starting upgrade from %s to %s", currentVersion, targetVersion));

        Object current = object;
        FormatVersion currentVer = currentVersion;

        // Collect all warnings from all steps
        List<String> allWarnings = new ArrayList<>();

        // Execute upgrade chain
        while (currentVer.compareTo(targetVersion) < 0) {
            UpgradeStep<Object, ?> step = (UpgradeStep<Object, ?>) upgradeSteps.get(currentVer);

            if (step == null) {
                throw new UpgradeException(currentVersion, targetVersion,
                    String.format("No upgrade step found from version %s", currentVer));
            }

            FormatVersion nextVersion = step.toVersion();
            BridgeLoggerHolder.getLogger().debug(String.format("Upgrading from %s to %s", currentVer, nextVersion));

            // Create a context for this specific step (with correct version pair)
            UpgradeContext stepContext = new UpgradeContext(currentVer, nextVersion);

            try {
                current = step.upgrade(current, stepContext);

                // Collect warnings from this step
                if (stepContext.hasWarnings()) {
                    allWarnings.addAll(stepContext.getWarnings());
                }

                currentVer = nextVersion;
            } catch (Exception e) {
                throw new UpgradeException(currentVer, nextVersion,
                    "Upgrade step failed", e);
            }
        }

        // Report all warnings at the end
        if (!allWarnings.isEmpty()) {
            BridgeLoggerHolder.getLogger().warning(String.format("Upgrade completed with %d warning(s)", allWarnings.size()));
            for (String warning : allWarnings) {
                BridgeLoggerHolder.getLogger().warning("  - " + warning);
            }
        } else {
            BridgeLoggerHolder.getLogger().info("Upgrade completed successfully with no warnings");
        }

        return (T) current;
    }

    /**
     * Check if an upgrade path exists from source to target version.
     */
    public boolean hasUpgradePath(FormatVersion fromVersion) {
        if (fromVersion.equals(targetVersion)) {
            return true;
        }

        FormatVersion current = fromVersion;
        while (current.compareTo(targetVersion) < 0) {
            if (!upgradeSteps.containsKey(current)) {
                return false;
            }
            current = upgradeSteps.get(current).toVersion();
        }
        return true;
    }

    /**
     * Get the target version this upgrader upgrades to.
     */
    public FormatVersion getTargetVersion() {
        return targetVersion;
    }

    /**
     * Get all registered upgrade steps.
     */
    public Collection<UpgradeStep<?, ?>> getUpgradeSteps() {
        return Collections.unmodifiableCollection(upgradeSteps.values());
    }
}
