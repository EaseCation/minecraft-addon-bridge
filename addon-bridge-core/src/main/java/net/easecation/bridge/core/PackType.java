package net.easecation.bridge.core;

/**
 * Represents the type of addon pack.
 */
public enum PackType {
    /**
     * Resource pack containing textures, models, sounds, etc.
     * These packs are pushed to clients.
     */
    RESOURCE,

    /**
     * Behavior pack containing server-side logic (blocks, entities, items, recipes, scripts).
     * These packs are NOT pushed to clients by default.
     * Mixed packs (containing both resource and behavior) are also classified as BEHAVIOR.
     */
    BEHAVIOR
}
