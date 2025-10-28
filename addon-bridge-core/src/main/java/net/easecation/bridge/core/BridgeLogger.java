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
     * Log a severe/error message.
     *
     * @param message the message to log
     */
    void severe(String message);

    /**
     * Log a fine/debug message.
     *
     * @param message the message to log
     */
    void fine(String message);

    /**
     * Log a finest/trace message.
     *
     * @param message the message to log
     */
    void finest(String message);

    /**
     * Log a severe/error message with throwable.
     *
     * @param message the message to log
     * @param throwable the exception to log
     */
    default void severe(String message, Throwable throwable) {
        severe(message + ": " + throwable.getMessage());
    }
}
