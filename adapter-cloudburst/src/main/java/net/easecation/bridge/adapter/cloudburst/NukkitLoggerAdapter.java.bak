package net.easecation.bridge.adapter.cloudburst;

import cn.nukkit.utils.Logger;
import net.easecation.bridge.core.BridgeLogger;

/**
 * Adapter that wraps Cloudburst Nukkit's Logger to BridgeLogger interface.
 */
public class NukkitLoggerAdapter implements BridgeLogger {
    private final Logger nukkitLogger;

    public NukkitLoggerAdapter(Logger nukkitLogger) {
        this.nukkitLogger = nukkitLogger;
    }

    @Override
    public void info(String message) {
        nukkitLogger.info(message);
    }

    @Override
    public void warning(String message) {
        nukkitLogger.warning(message);
    }

    @Override
    public void severe(String message) {
        nukkitLogger.error(message);
    }

    @Override
    public void fine(String message) {
        nukkitLogger.debug(message);
    }

    @Override
    public void finest(String message) {
        // Nukkit doesn't have trace level, use debug
        nukkitLogger.debug(message);
    }

    @Override
    public void severe(String message, Throwable throwable) {
        nukkitLogger.error(message, throwable);
    }
}
