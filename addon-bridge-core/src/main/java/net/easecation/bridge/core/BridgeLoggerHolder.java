package net.easecation.bridge.core;

/**
 * 全局Logger持有者，提供单例模式的Logger访问
 *
 * 使用方式：
 * 1. 在插件初始化时调用 setLogger() 设置Logger实例
 * 2. 在任何需要日志的地方调用 getLogger() 获取Logger
 *
 * 线程安全：使用volatile和synchronized保证多线程环境下的安全性
 */
public final class BridgeLoggerHolder {

    /**
     * 全局Logger实例，使用volatile保证可见性
     */
    private static volatile BridgeLogger logger;

    /**
     * 默认的fallback logger，在未初始化时使用
     */
    private static final BridgeLogger DEFAULT_LOGGER = new BridgeLogger() {
        @Override
        public void info(String message) {
            System.out.println("[INFO] " + message);
        }

        @Override
        public void warning(String message) {
            System.out.println("[WARN] " + message);
        }

        @Override
        public void error(String message) {
            System.err.println("[ERROR] " + message);
        }

        @Override
        public void debug(String message) {
            System.out.println("[DEBUG] " + message);
        }

        @Override
        public void trace(String message) {
            System.out.println("[TRACE] " + message);
        }
    };

    // 私有构造函数，防止实例化
    private BridgeLoggerHolder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 设置全局Logger实例
     *
     * @param newLogger 新的Logger实例，不能为null
     * @throws IllegalArgumentException 如果newLogger为null
     */
    public static synchronized void setLogger(BridgeLogger newLogger) {
        if (newLogger == null) {
            throw new IllegalArgumentException("Logger cannot be null");
        }
        logger = newLogger;
    }

    /**
     * 获取全局Logger实例
     *
     * @return Logger实例，如果未初始化则返回默认Logger
     */
    public static BridgeLogger getLogger() {
        // 使用局部变量避免多次读取volatile字段
        BridgeLogger result = logger;
        if (result == null) {
            // 返回默认logger，避免NPE
            return DEFAULT_LOGGER;
        }
        return result;
    }

    /**
     * 检查Logger是否已初始化
     *
     * @return 如果已设置Logger返回true，否则返回false
     */
    public static boolean isInitialized() {
        return logger != null;
    }

    /**
     * 重置Logger（主要用于测试场景）
     */
    public static synchronized void reset() {
        logger = null;
    }
}
