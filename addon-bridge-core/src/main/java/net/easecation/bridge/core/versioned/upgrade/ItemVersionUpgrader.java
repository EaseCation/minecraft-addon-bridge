package net.easecation.bridge.core.versioned.upgrade;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.easecation.bridge.core.versioned.FormatVersion;

/**
 * Version upgrader specifically for Item DTOs.
 * Manages all upgrade steps from v1.19.0 to v1.21.60.
 */
public class ItemVersionUpgrader extends VersionUpgrader<net.easecation.bridge.core.dto.item.v1_21_60.ItemsDefinition> {

    private static final ItemVersionUpgrader INSTANCE = new ItemVersionUpgrader();

    private ObjectMapper mapper;
    private boolean stepsRegistered = false;

    private ItemVersionUpgrader() {
        super(FormatVersion.V1_21_60);
    }

    /**
     * Get the singleton instance of ItemVersionUpgrader.
     */
    public static ItemVersionUpgrader getInstance() {
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
     * Register all 7 upgrade steps for Item module.
     */
    private void registerAllSteps() {
        if (mapper == null) {
            throw new IllegalStateException("ObjectMapper must be set before registering steps");
        }
        // Step 1: v1.19.0 -> v1.19.40
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_0,
            FormatVersion.V1_19_40,
            net.easecation.bridge.core.dto.item.v1_19_0.ItemsDefinition.class,
            net.easecation.bridge.core.dto.item.v1_19_40.ItemsDefinition.class,
            mapper
        ));

        // Step 2: v1.19.40 -> v1.19.50
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_40,
            FormatVersion.V1_19_50,
            net.easecation.bridge.core.dto.item.v1_19_40.ItemsDefinition.class,
            net.easecation.bridge.core.dto.item.v1_19_50.ItemsDefinition.class,
            mapper
        ));

        // Step 3: v1.19.50 -> v1.20.10
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_19_50,
            FormatVersion.V1_20_10,
            net.easecation.bridge.core.dto.item.v1_19_50.ItemsDefinition.class,
            net.easecation.bridge.core.dto.item.v1_20_10.ItemsDefinition.class,
            mapper
        ));

        // Step 4: v1.20.10 -> v1.20.41
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_20_10,
            FormatVersion.V1_20_41,
            net.easecation.bridge.core.dto.item.v1_20_10.ItemsDefinition.class,
            net.easecation.bridge.core.dto.item.v1_20_41.ItemsDefinition.class,
            mapper
        ));

        // Step 5: v1.20.41 -> v1.20.81
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_20_41,
            FormatVersion.V1_20_81,
            net.easecation.bridge.core.dto.item.v1_20_41.ItemsDefinition.class,
            net.easecation.bridge.core.dto.item.v1_20_81.ItemsDefinition.class,
            mapper
        ));

        // Step 6: v1.20.81 -> v1.21.50
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_20_81,
            FormatVersion.V1_21_50,
            net.easecation.bridge.core.dto.item.v1_20_81.ItemsDefinition.class,
            net.easecation.bridge.core.dto.item.v1_21_50.ItemsDefinition.class,
            mapper
        ));

        // Step 7: v1.21.50 -> v1.21.60
        registerStep(new GenericUpgradeStep<>(
            FormatVersion.V1_21_50,
            FormatVersion.V1_21_60,
            net.easecation.bridge.core.dto.item.v1_21_50.ItemsDefinition.class,
            net.easecation.bridge.core.dto.item.v1_21_60.ItemsDefinition.class,
            mapper
        ));
    }

    /**
     * Convenience method to upgrade an ItemsDefinition from any supported version to v1.21.60.
     *
     * @param item The item object at its current version
     * @param currentVersion The format version of the item
     * @return The upgraded item at v1.21.60
     * @throws UpgradeException if upgrade fails
     */
    public static net.easecation.bridge.core.dto.item.v1_21_60.ItemsDefinition upgradeToLatest(
        Object item,
        FormatVersion currentVersion
    ) throws UpgradeException {
        return getInstance().upgrade(item, currentVersion);
    }
}
