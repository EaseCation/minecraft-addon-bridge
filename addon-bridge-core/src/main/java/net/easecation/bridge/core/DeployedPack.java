package net.easecation.bridge.core;

/**
 * Represents a deployed addon pack with its URL, SHA1 hash, and pack type.
 *
 * @param url The URL of the pack (file:// or HTTP URL)
 * @param sha1 The SHA1 hash of the pack file
 * @param packType The type of pack (RESOURCE or BEHAVIOR)
 */
public record DeployedPack(String url, String sha1, PackType packType) {}

