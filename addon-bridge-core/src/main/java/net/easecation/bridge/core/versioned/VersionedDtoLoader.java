package net.easecation.bridge.core.versioned;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.easecation.bridge.core.BridgeLogger;
import net.easecation.bridge.core.versioned.upgrade.*;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to load and upgrade versioned DTOs.
 * Maps format_version strings to appropriate DTO classes and handles upgrades.
 */
public class VersionedDtoLoader {

    private final ObjectMapper mapper;
    private BridgeLogger logger;

    // Version to class mapping for each module
    private static final Map<String, Class<?>> ENTITY_CLASSES = new HashMap<>();
    private static final Map<String, Class<?>> ITEM_CLASSES = new HashMap<>();
    private static final Map<String, Class<?>> BLOCK_CLASSES = new HashMap<>();

    static {
        // Entity version mapping
        ENTITY_CLASSES.put("v1_19_0", net.easecation.bridge.core.dto.entity.v1_19_0.Entity.class);
        ENTITY_CLASSES.put("v1_19_40", net.easecation.bridge.core.dto.entity.v1_19_40.Entity.class);
        ENTITY_CLASSES.put("v1_19_50", net.easecation.bridge.core.dto.entity.v1_19_50.Entity.class);
        ENTITY_CLASSES.put("v1_20_10", net.easecation.bridge.core.dto.entity.v1_20_10.Entity.class);
        ENTITY_CLASSES.put("v1_20_41", net.easecation.bridge.core.dto.entity.v1_20_41.Entity.class);
        ENTITY_CLASSES.put("v1_20_81", net.easecation.bridge.core.dto.entity.v1_20_81.Entity.class);
        ENTITY_CLASSES.put("v1_21_50", net.easecation.bridge.core.dto.entity.v1_21_50.Entity.class);
        ENTITY_CLASSES.put("v1_21_60", net.easecation.bridge.core.dto.entity.v1_21_60.Entity.class);

        // Item version mapping
        ITEM_CLASSES.put("v1_19_0", net.easecation.bridge.core.dto.item.v1_19_0.ItemsDefinition.class);
        ITEM_CLASSES.put("v1_19_40", net.easecation.bridge.core.dto.item.v1_19_40.ItemsDefinition.class);
        ITEM_CLASSES.put("v1_19_50", net.easecation.bridge.core.dto.item.v1_19_50.ItemsDefinition.class);
        ITEM_CLASSES.put("v1_20_10", net.easecation.bridge.core.dto.item.v1_20_10.ItemsDefinition.class);
        ITEM_CLASSES.put("v1_20_41", net.easecation.bridge.core.dto.item.v1_20_41.ItemsDefinition.class);
        ITEM_CLASSES.put("v1_20_81", net.easecation.bridge.core.dto.item.v1_20_81.ItemsDefinition.class);
        ITEM_CLASSES.put("v1_21_50", net.easecation.bridge.core.dto.item.v1_21_50.ItemsDefinition.class);
        ITEM_CLASSES.put("v1_21_60", net.easecation.bridge.core.dto.item.v1_21_60.ItemsDefinition.class);

        // Block version mapping
        BLOCK_CLASSES.put("v1_19_0", net.easecation.bridge.core.dto.block.v1_19_0.BlockDefinitions.class);
        BLOCK_CLASSES.put("v1_19_40", net.easecation.bridge.core.dto.block.v1_19_40.BlockDefinitions.class);
        BLOCK_CLASSES.put("v1_19_50", net.easecation.bridge.core.dto.block.v1_19_50.BlockDefinitions.class);
        BLOCK_CLASSES.put("v1_20_10", net.easecation.bridge.core.dto.block.v1_20_10.BlockDefinitions.class);
        BLOCK_CLASSES.put("v1_20_41", net.easecation.bridge.core.dto.block.v1_20_41.BlockDefinitions.class);
        BLOCK_CLASSES.put("v1_20_81", net.easecation.bridge.core.dto.block.v1_20_81.BlockDefinitions.class);
        BLOCK_CLASSES.put("v1_21_50", net.easecation.bridge.core.dto.block.v1_21_50.BlockDefinitions.class);
        BLOCK_CLASSES.put("v1_21_60", net.easecation.bridge.core.dto.block.v1_21_60.BlockDefinitions.class);
    }

    public VersionedDtoLoader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Set the logger for upgrade operations.
     */
    public void setLogger(BridgeLogger logger) {
        this.logger = logger;
    }

    /**
     * Load and upgrade an Entity DTO from raw data.
     *
     * @param entityData The raw entity data from JSON
     * @param formatVersion The format_version string (e.g., "1.19.0"), or null to use latest
     * @return The upgraded Entity at v1.21.60
     * @throws Exception if loading or upgrade fails
     */
    public net.easecation.bridge.core.dto.entity.v1_21_60.Entity loadEntity(
        Object entityData,
        @Nullable String formatVersion
    ) throws Exception {
        FormatVersion version = parseVersion(formatVersion);

        // If already at target version, directly convert
        if (version.equals(FormatVersion.V1_21_60)) {
            return mapper.convertValue(entityData, net.easecation.bridge.core.dto.entity.v1_21_60.Entity.class);
        }

        // Load appropriate version DTO
        Class<?> entityClass = ENTITY_CLASSES.get(version.toPackageName());
        if (entityClass == null) {
            throw new IllegalArgumentException("Unsupported entity format_version: " + formatVersion);
        }

        Object versionedEntity = mapper.convertValue(entityData, entityClass);

        // Upgrade to latest version
        EntityVersionUpgrader upgrader = EntityVersionUpgrader.getInstance();
        upgrader.setLogger(logger);
        return upgrader.upgrade(versionedEntity, version);
    }

    /**
     * Load and upgrade an ItemsDefinition DTO from raw data.
     *
     * @param itemData The raw item data from JSON (the full ItemsDefinition with format_version)
     * @param formatVersion The format_version string, or null to use latest
     * @return The upgraded ItemsDefinition at v1.21.60
     * @throws Exception if loading or upgrade fails
     */
    public net.easecation.bridge.core.dto.item.v1_21_60.ItemsDefinition loadItem(
        Object itemData,
        @Nullable String formatVersion
    ) throws Exception {
        FormatVersion version = parseVersion(formatVersion);

        // If already at target version, directly convert
        if (version.equals(FormatVersion.V1_21_60)) {
            return mapper.convertValue(itemData, net.easecation.bridge.core.dto.item.v1_21_60.ItemsDefinition.class);
        }

        // Load appropriate version DTO
        Class<?> itemClass = ITEM_CLASSES.get(version.toPackageName());
        if (itemClass == null) {
            throw new IllegalArgumentException("Unsupported item format_version: " + formatVersion);
        }

        Object versionedItem = mapper.convertValue(itemData, itemClass);

        // Upgrade to latest version
        ItemVersionUpgrader upgrader = ItemVersionUpgrader.getInstance();
        upgrader.setLogger(logger);
        return upgrader.upgrade(versionedItem, version);
    }

    /**
     * Load and upgrade a Block DTO from raw data.
     *
     * @param blockData The raw block data from JSON
     * @param formatVersion The format_version string, or null to use latest
     * @return The upgraded BlockDefinitions at v1.21.60
     * @throws Exception if loading or upgrade fails
     */
    public net.easecation.bridge.core.dto.block.v1_21_60.BlockDefinitions loadBlock(
        Object blockData,
        @Nullable String formatVersion
    ) throws Exception {
        FormatVersion version = parseVersion(formatVersion);

        // If already at target version, directly convert
        if (version.equals(FormatVersion.V1_21_60)) {
            return mapper.convertValue(blockData, net.easecation.bridge.core.dto.block.v1_21_60.BlockDefinitions.class);
        }

        // Load appropriate version DTO
        Class<?> blockClass = BLOCK_CLASSES.get(version.toPackageName());
        if (blockClass == null) {
            throw new IllegalArgumentException("Unsupported block format_version: " + formatVersion);
        }

        Object versionedBlock = mapper.convertValue(blockData, blockClass);

        // Upgrade to latest version
        BlockVersionUpgrader upgrader = BlockVersionUpgrader.getInstance();
        upgrader.setLogger(logger);
        return upgrader.upgrade(versionedBlock, version);
    }

    // Sorted list of supported versions (from oldest to newest)
    private static final FormatVersion[] SUPPORTED_VERSIONS = {
        FormatVersion.V1_19_0,
        FormatVersion.V1_19_40,
        FormatVersion.V1_19_50,
        FormatVersion.V1_20_10,
        FormatVersion.V1_20_41,
        FormatVersion.V1_20_81,
        FormatVersion.V1_21_50,
        FormatVersion.V1_21_60
    };

    /**
     * Parse format_version string to FormatVersion object.
     * If the exact version is not supported, finds the closest lower version.
     * Returns DEFAULT version if null or invalid.
     */
    private FormatVersion parseVersion(@Nullable String formatVersionStr) {
        if (formatVersionStr == null || formatVersionStr.isEmpty()) {
            return FormatVersion.DEFAULT;
        }

        FormatVersion version = FormatVersion.parse(formatVersionStr);
        if (version == null) {
            if (logger != null) {
                logger.warning(String.format("Invalid format_version '%s', using default %s",
                    formatVersionStr, FormatVersion.DEFAULT));
            }
            return FormatVersion.DEFAULT;
        }

        // Check if exact version is supported
        String packageName = version.toPackageName();
        if (ENTITY_CLASSES.containsKey(packageName)) {
            return version;
        }

        // Find the closest lower version
        FormatVersion closestVersion = findClosestLowerVersion(version);
        if (closestVersion != null) {
            if (logger != null) {
                logger.info(String.format("format_version %s not directly supported, using closest version %s",
                    version, closestVersion));
            }
            return closestVersion;
        }

        // If no lower version found, use the oldest supported version
        if (logger != null) {
            logger.warning(String.format("format_version %s is older than all supported versions, using %s",
                version, SUPPORTED_VERSIONS[0]));
        }
        return SUPPORTED_VERSIONS[0];
    }

    /**
     * Find the closest version that is lower than or equal to the target version.
     * Returns null if no lower version exists.
     */
    private FormatVersion findClosestLowerVersion(FormatVersion targetVersion) {
        FormatVersion closest = null;

        for (FormatVersion supportedVersion : SUPPORTED_VERSIONS) {
            if (supportedVersion.compareTo(targetVersion) <= 0) {
                // This version is lower or equal, keep it as candidate
                closest = supportedVersion;
            } else {
                // We've gone past the target, stop searching
                break;
            }
        }

        return closest;
    }
}
