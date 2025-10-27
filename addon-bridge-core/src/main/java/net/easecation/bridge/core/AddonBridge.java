package net.easecation.bridge.core;

import java.io.File;

public interface AddonBridge {
    void loadAndRegisterAll(File addonsRoot) throws Exception;
}

