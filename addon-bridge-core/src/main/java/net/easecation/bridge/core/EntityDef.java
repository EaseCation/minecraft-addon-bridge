package net.easecation.bridge.core;

import java.util.Map;

public record EntityDef(String id, double health, double speed, Map<String, Object> ai) {}

