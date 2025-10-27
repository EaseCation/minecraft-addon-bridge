package net.easecation.bridge.core;

import java.util.List;

public interface ResourcePackDeployer {
    List<DeployedPack> deploy(AddonPack pack) throws Exception;
}

