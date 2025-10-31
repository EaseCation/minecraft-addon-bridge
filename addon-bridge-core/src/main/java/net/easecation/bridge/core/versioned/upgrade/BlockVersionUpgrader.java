package net.easecation.bridge.core.versioned.upgrade;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.easecation.bridge.core.versioned.FormatVersion;

/**
 * Version upgrader specifically for Block DTOs.
 * Manages all upgrade steps from v1.19.0 to v1.21.60.
 */
public class BlockVersionUpgrader extends VersionUpgrader<net.easecation.bridge.core.dto.block.v1_21_60.BlockDefinitions> {

    private static final BlockVersionUpgrader INSTANCE = new BlockVersionUpgrader();

    private ObjectMapper mapper;
    private boolean stepsRegistered = false;

    private BlockVersionUpgrader() {
        super(FormatVersion.V1_21_60);
    }

    /**
     * Get the singleton instance of BlockVersionUpgrader.
     */
    public static BlockVersionUpgrader getInstance() {
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
     * Register all 7 upgrade steps for Block module.
     */
    private void registerAllSteps() {
        if (mapper == null) {
            throw new IllegalStateException("ObjectMapper must be set before registering steps");
        }
        // Step 1: v1.19.0 -> v1.19.40
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_0,
            FormatVersion.V1_19_40,
            net.easecation.bridge.core.dto.block.v1_19_0.BlockDefinitions.class,
            net.easecation.bridge.core.dto.block.v1_19_40.BlockDefinitions.class,
            mapper
        ));

        // Step 2: v1.19.40 -> v1.19.50
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_40,
            FormatVersion.V1_19_50,
            net.easecation.bridge.core.dto.block.v1_19_40.BlockDefinitions.class,
            net.easecation.bridge.core.dto.block.v1_19_50.BlockDefinitions.class,
            mapper
        ));

        // Step 3: v1.19.50 -> v1.20.10
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_50,
            FormatVersion.V1_20_10,
            net.easecation.bridge.core.dto.block.v1_19_50.BlockDefinitions.class,
            net.easecation.bridge.core.dto.block.v1_20_10.BlockDefinitions.class,
            mapper
        ));

        // Step 4: v1.20.10 -> v1.20.41
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_20_10,
            FormatVersion.V1_20_41,
            net.easecation.bridge.core.dto.block.v1_20_10.BlockDefinitions.class,
            net.easecation.bridge.core.dto.block.v1_20_41.BlockDefinitions.class,
            mapper
        ));

        // Step 5: v1.20.41 -> v1.20.81
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_20_41,
            FormatVersion.V1_20_81,
            net.easecation.bridge.core.dto.block.v1_20_41.BlockDefinitions.class,
            net.easecation.bridge.core.dto.block.v1_20_81.BlockDefinitions.class,
            mapper
        ));

        // Step 6: v1.20.81 -> v1.21.50
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_20_81,
            FormatVersion.V1_21_50,
            net.easecation.bridge.core.dto.block.v1_20_81.BlockDefinitions.class,
            net.easecation.bridge.core.dto.block.v1_21_50.BlockDefinitions.class,
            mapper
        ));

        // Step 7: v1.21.50 -> v1.21.60
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_21_50,
            FormatVersion.V1_21_60,
            net.easecation.bridge.core.dto.block.v1_21_50.BlockDefinitions.class,
            net.easecation.bridge.core.dto.block.v1_21_60.BlockDefinitions.class,
            mapper
        ));
    }

    /**
     * Convenience method to upgrade a BlockDefinitions from any supported version to v1.21.60.
     *
     * @param block The block object at its current version
     * @param currentVersion The format version of the block
     * @return The upgraded block at v1.21.60
     * @throws UpgradeException if upgrade fails
     */
    public static net.easecation.bridge.core.dto.block.v1_21_60.BlockDefinitions upgradeToLatest(
        Object block,
        FormatVersion currentVersion
    ) throws UpgradeException {
        return getInstance().upgrade(block, currentVersion);
    }
}
