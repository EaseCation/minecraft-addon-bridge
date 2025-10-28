package net.easecation.bridge.core;

import java.io.File;
import java.util.List;

public interface AddonBridge {
    /**
     * 加载并注册所有插件包
     * Loads and registers all addon packs
     *
     * @param addonsRoot 插件包根目录
     * @return 已部署的资源包列表，包含URL和SHA1信息
     * @throws Exception 加载或注册过程中的异常
     */
    List<DeployedPack> loadAndRegisterAll(File addonsRoot) throws Exception;
}

