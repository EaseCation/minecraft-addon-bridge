package net.easecation.bridge.core.versioned.upgrade;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.easecation.bridge.core.versioned.FormatVersion;

/**
 * Version upgrader specifically for Entity DTOs.
 * Manages all upgrade steps from v1.19.0 to v1.21.60.
 */
public class EntityVersionUpgrader extends VersionUpgrader<net.easecation.bridge.core.dto.entity.v1_21_60.Entity> {

    private static final EntityVersionUpgrader INSTANCE = new EntityVersionUpgrader();

    private ObjectMapper mapper;
    private boolean stepsRegistered = false;

    private EntityVersionUpgrader() {
        super(FormatVersion.V1_21_60);
    }

    /**
     * Get the singleton instance of EntityVersionUpgrader.
     */
    public static EntityVersionUpgrader getInstance() {
        return INSTANCE;
    }

    /**
     * Set the ObjectMapper for this upgrader. This must be called before upgrade operations.
     * Will register all upgrade steps if not already done.
     */
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
        if (!stepsRegistered) {
            registerAllSteps();
            stepsRegistered = true;
        }
    }

    /**
     * Register all 7 upgrade steps for Entity module.
     */
    private void registerAllSteps() {
        if (mapper == null) {
            throw new IllegalStateException("ObjectMapper must be set before registering steps");
        }
        // Step 1: v1.19.0 -> v1.19.40 (Generic upgrade - just new fields)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_0,
            FormatVersion.V1_19_40,
            net.easecation.bridge.core.dto.entity.v1_19_0.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_19_40.Entity.class,
            mapper
        ));

        // Step 2: v1.19.40 -> v1.19.50 (Generic upgrade)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_40,
            FormatVersion.V1_19_50,
            net.easecation.bridge.core.dto.entity.v1_19_40.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_19_50.Entity.class,
            mapper
        ));

        // Step 3: v1.19.50 -> v1.20.10 (Generic upgrade)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_50,
            FormatVersion.V1_20_10,
            net.easecation.bridge.core.dto.entity.v1_19_50.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_20_10.Entity.class,
            mapper
        ));

        // Step 4: v1.20.10 -> v1.20.41 (Generic upgrade)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_20_10,
            FormatVersion.V1_20_41,
            net.easecation.bridge.core.dto.entity.v1_20_10.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_20_41.Entity.class,
            mapper
        ));

        // Step 5: v1.20.41 -> v1.20.81 (Manual upgrade - Components type changes)
        // In this version, 7 component types changed from specific types to Attribute:
        // Health, AttackDamage, FollowRange, KnockbackResistance, LavaMovement, Movement, UnderwaterMovement
        registerStep(new EntityComponentsUpgradeStep_v1_20_41_to_v1_20_81(mapper));

        // Step 6: v1.20.81 -> v1.21.50 (JSON-based upgrade - Range_a_B cross-version)
        // Range_a_B type is structurally identical but from different packages
        // JSON conversion handles automatic type recreation
        registerStep(new GenericUpgradeStep<>(
                FormatVersion.V1_20_81,
                FormatVersion.V1_21_50,
                net.easecation.bridge.core.dto.entity.v1_20_81.Entity.class,
                net.easecation.bridge.core.dto.entity.v1_21_50.Entity.class,
                mapper
        ));

        // Step 7: v1.21.50 -> v1.21.60 (Generic upgrade)
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_21_50,
            FormatVersion.V1_21_60,
            net.easecation.bridge.core.dto.entity.v1_21_50.Entity.class,
            net.easecation.bridge.core.dto.entity.v1_21_60.Entity.class,
            mapper
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
