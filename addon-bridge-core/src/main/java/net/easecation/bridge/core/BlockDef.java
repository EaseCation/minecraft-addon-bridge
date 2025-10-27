package net.easecation.bridge.core;

import java.util.Map;

public record BlockDef(String id, Map<String, Object> states, double hardness) {}

