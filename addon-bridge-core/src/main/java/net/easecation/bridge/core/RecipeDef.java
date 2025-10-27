package net.easecation.bridge.core;

import java.util.Map;

public record RecipeDef(String id, String type, Map<String, Object> data) {}

