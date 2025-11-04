package net.easecation.bridge.adapter.pnx.mapper;

import net.easecation.bridge.core.BridgeLogger;
import net.easecation.bridge.core.BridgeLoggerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 组件映射上下文，用于在映射过程中传递状态和收集信息
 */
public class MappingContext {
    private final String objectId; // Block/Entity/Item的ID
    private final List<String> unmappedComponents;
    private final List<String> warnings;

    public MappingContext(String objectId) {
        this.objectId = objectId;
        this.unmappedComponents = new ArrayList<>();
        this.warnings = new ArrayList<>();
    }

    /**
     * 记录未映射的组件
     */
    public void recordUnmappedComponent(String componentName, String reason) {
        String message = String.format("Component '%s' in '%s': %s", componentName, objectId, reason);
        unmappedComponents.add(message);
        BridgeLoggerHolder.getLogger().warning("[PNX Mapper] " + message);
    }

    /**
     * 记录警告信息
     */
    public void warn(String message) {
        warnings.add(message);
        BridgeLoggerHolder.getLogger().warning("[PNX Mapper] [" + objectId + "] " + message);
    }

    /**
     * 记录信息日志
     */
    public void info(String message) {
        BridgeLoggerHolder.getLogger().info("[PNX Mapper] [" + objectId + "] " + message);
    }

    public BridgeLogger getLogger() {
        return BridgeLoggerHolder.getLogger();
    }

    public String getObjectId() {
        return objectId;
    }

    public List<String> getUnmappedComponents() {
        return new ArrayList<>(unmappedComponents);
    }

    public List<String> getWarnings() {
        return new ArrayList<>(warnings);
    }

    public boolean hasUnmappedComponents() {
        return !unmappedComponents.isEmpty();
    }
}
