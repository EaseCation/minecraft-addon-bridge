package net.easecation.bridge.core;

/**
 * Unified logging interface for AddonBridge.
 * Allows adapters to use their respective Nukkit Logger implementations
 * while keeping the core module independent.
 */
public interface BridgeLogger {
    /**
     * Log an informational message.
     *
     * @param message the message to log
     */
    void info(String message);

    /**
     * Log a warning message.
     *
     * @param message the message to log
     */
    void warning(String message);

    /**
     * Log an error message.
     *
     * @param message the message to log
     */
    void error(String message);

    /**
     * Log a debug message.
     *
     * @param message the message to log
     */
    void debug(String message);

    /**
     * Log a trace message.
     *
     * @param message the message to log
     */
    void trace(String message);

    /**
     * Log an error message with throwable.
     *
     * @param message the message to log
     * @param throwable the exception to log
     */
    default void error(String message, Throwable throwable) {
        error(message + ": " + throwable.getMessage());
    }
}
