package net.easecation.bridge.core.versioned.upgrade;

import net.easecation.bridge.core.versioned.FormatVersion;

/**
 * Version upgrader specifically for Entity DTOs.
 * Manages all upgrade steps from v1.19.0 to v1.21.60.
 */
public class EntityVersionUpgrader extends VersionUpgrader<net.easecation.bridge.core.dto.entity.v1_21_60.Entity> {

    private static final EntityVersionUpgrader INSTANCE = new EntityVersionUpgrader();

    private EntityVersionUpgrader() {
        super(FormatVersion.V1_21_60);
        registerAllSteps();
    }

    /**
     * Get the singleton instance of EntityVersionUpgrader.
     */
    public static EntityVersionUpgrader getInstance() {
        return INSTANCE;
    }

    /**
     * Register all 7 upgrade steps for Entity module.
     */
    private void registerAllSteps() {
        // Step 1: v1.19.0 -> v1.19.40 (Generic upgrade - just new fields)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_0,
            FormatVersion.V1_19_40,
            net.easecation.bridge.core.dto.entity.v1_19_0.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_19_40.Entity.class
        ));

        // Step 2: v1.19.40 -> v1.19.50 (Generic upgrade)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_40,
            FormatVersion.V1_19_50,
            net.easecation.bridge.core.dto.entity.v1_19_40.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_19_50.Entity.class
        ));

        // Step 3: v1.19.50 -> v1.20.10 (Generic upgrade)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_50,
            FormatVersion.V1_20_10,
            net.easecation.bridge.core.dto.entity.v1_19_50.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_20_10.Entity.class
        ));

        // Step 4: v1.20.10 -> v1.20.41 (Generic upgrade)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_20_10,
            FormatVersion.V1_20_41,
            net.easecation.bridge.core.dto.entity.v1_20_10.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_20_41.Entity.class
        ));

        // Step 5: v1.20.41 -> v1.20.81 (Generic upgrade)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_20_41,
            FormatVersion.V1_20_81,
            net.easecation.bridge.core.dto.entity.v1_20_41.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_20_81.Entity.class
        ));

        // Step 6: v1.20.81 -> v1.21.50 (Generic upgrade)
        // Note: Health -> Attribute type changes may cause warnings, but GenericUpgradeStep
        // will set incompatible fields to null and warn
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_20_81,
            FormatVersion.V1_21_50,
            net.easecation.bridge.core.dto.entity.v1_20_81.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_21_50.Entity.class
        ));

        // Step 7: v1.21.50 -> v1.21.60 (Generic upgrade)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_21_50,
            FormatVersion.V1_21_60,
            net.easecation.bridge.core.dto.entity.v1_21_50.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_21_60.Entity.class
        ));
    }

    /**
     * Convenience method to upgrade an Entity from any supported version to v1.21.60.
     *
     * @param entity The entity object at its current version
     * @param currentVersion The format version of the entity
     * @return The upgraded entity at v1.21.60
     * @throws UpgradeException if upgrade fails
     */
    public static net.easecation.bridge.core.dto.entity.v1_21_60.Entity upgradeToLatest(
        Object entity,
        FormatVersion currentVersion
    ) throws UpgradeException {
        return getInstance().upgrade(entity, currentVersion);
    }
}
