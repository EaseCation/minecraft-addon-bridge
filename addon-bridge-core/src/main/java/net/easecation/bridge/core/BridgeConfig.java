package net.easecation.bridge.core;

/**
 * Configuration for AddonBridge plugin.
 * Contains settings for resource pack pushing and plugin behavior.
 */
public class BridgeConfig {
    // Resource pack pushing settings
    private boolean pushResourcePacks;
    private boolean pushBehaviorPacks;
    private boolean forceAccept;
    private int pushPriority;
    private String baseUrl;

    // Plugin behavior settings
    private boolean autoScan;
    private boolean verboseLogging;

    // Custom content registration settings
    private boolean registerBlocks;
    private boolean registerEntities;
    private boolean registerItems;

    /**
     * Creates a default configuration with recommended settings.
     */
    public BridgeConfig() {
        this.pushResourcePacks = true;
        this.pushBehaviorPacks = false;
        this.forceAccept = true;
        this.pushPriority = 0;
        this.baseUrl = "";
        this.autoScan = true;
        this.verboseLogging = false;
        this.registerBlocks = true;
        this.registerEntities = true;
        this.registerItems = true;
    }

    // Getters
    public boolean isPushResourcePacks() {
        return pushResourcePacks;
    }

    public boolean isPushBehaviorPacks() {
        return pushBehaviorPacks;
    }

    public boolean isForceAccept() {
        return forceAccept;
    }

    public int getPushPriority() {
        return pushPriority;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public boolean isAutoScan() {
        return autoScan;
    }

    public boolean isVerboseLogging() {
        return verboseLogging;
    }

    public boolean isRegisterBlocks() {
        return registerBlocks;
    }

    public boolean isRegisterEntities() {
        return registerEntities;
    }

    public boolean isRegisterItems() {
        return registerItems;
    }

    // Setters (for builder pattern or config loading)
    public void setPushResourcePacks(boolean pushResourcePacks) {
        this.pushResourcePacks = pushResourcePacks;
    }

    public void setPushBehaviorPacks(boolean pushBehaviorPacks) {
        this.pushBehaviorPacks = pushBehaviorPacks;
    }

    public void setForceAccept(boolean forceAccept) {
        this.forceAccept = forceAccept;
    }

    public void setPushPriority(int pushPriority) {
        this.pushPriority = pushPriority;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl != null ? baseUrl : "";
    }

    public void setAutoScan(boolean autoScan) {
        this.autoScan = autoScan;
    }

    public void setVerboseLogging(boolean verboseLogging) {
        this.verboseLogging = verboseLogging;
    }

    public void setRegisterBlocks(boolean registerBlocks) {
        this.registerBlocks = registerBlocks;
    }

    public void setRegisterEntities(boolean registerEntities) {
        this.registerEntities = registerEntities;
    }

    public void setRegisterItems(boolean registerItems) {
        this.registerItems = registerItems;
    }

    /**
     * Determines if a pack should be pushed to clients based on its type and configuration.
     *
     * @param packType The type of pack (RESOURCE or BEHAVIOR)
     * @return true if the pack should be pushed, false otherwise
     */
    public boolean shouldPushPack(PackType packType) {
        return switch (packType) {
            case RESOURCE -> pushResourcePacks;
            case BEHAVIOR -> pushBehaviorPacks;
        };
    }

    @Override
    public String toString() {
        return "BridgeConfig{" +
                "pushResourcePacks=" + pushResourcePacks +
                ", pushBehaviorPacks=" + pushBehaviorPacks +
                ", forceAccept=" + forceAccept +
                ", pushPriority=" + pushPriority +
                ", baseUrl='" + baseUrl + '\'' +
                ", autoScan=" + autoScan +
                ", verboseLogging=" + verboseLogging +
                ", registerBlocks=" + registerBlocks +
                ", registerEntities=" + registerEntities +
                ", registerItems=" + registerItems +
                '}';
    }
}
