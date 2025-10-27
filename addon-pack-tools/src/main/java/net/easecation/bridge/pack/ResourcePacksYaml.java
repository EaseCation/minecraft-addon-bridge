package net.easecation.bridge.pack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Minimal YAML writer for resource_packs.yml with URL and SHA1. */
public final class ResourcePacksYaml {
    private ResourcePacksYaml() {}

    public static void updateOrAppend(Path yamlPath, String url, String sha1) throws IOException {
        List<String> lines = new ArrayList<>();
        if (Files.exists(yamlPath)) {
            lines.addAll(Files.readAllLines(yamlPath, StandardCharsets.UTF_8));
        } else {
            lines.add("resource_stack:");
        }
        String entryPrefix = "  - { url: \"" + url + "\", sha1: \"" + sha1 + "\" }";
        lines.add(entryPrefix);
        Files.createDirectories(yamlPath.getParent());
        Files.writeString(yamlPath, String.join(System.lineSeparator(), lines), StandardCharsets.UTF_8);
    }
}

