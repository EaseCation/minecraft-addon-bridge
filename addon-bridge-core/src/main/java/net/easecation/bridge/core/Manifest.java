package net.easecation.bridge.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Represents the manifest.json structure for Minecraft Bedrock addons.
 * Contains metadata, modules, dependencies and capabilities.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Manifest(
        @JsonProperty("format_version") int formatVersion,
        @JsonProperty("header") Header header,
        @JsonProperty("modules") List<Module> modules,
        @JsonProperty("dependencies") @Nullable List<Dependency> dependencies,
        @JsonProperty("metadata") @Nullable Metadata metadata,
        @JsonProperty("capabilities") @Nullable List<String> capabilities
) {
    // Legacy constructor for backward compatibility
    public Manifest(String uuid, String name, String version) {
        this(
                1,
                new Header(uuid, name, version, 0, 0, 0),
                List.of(),
                null,
                null,
                null
        );
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Header(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("name") String name,
            @JsonProperty("description") @Nullable String description,
            @JsonProperty("version") List<Integer> version,
            @JsonProperty("min_engine_version") @Nullable List<Integer> minEngineVersion
    ) {
        // Constructor for legacy string version
        public Header(String uuid, String name, String version, int major, int minor, int patch) {
            this(uuid, name, null, List.of(major, minor, patch), null);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Module(
            @JsonProperty("type") String type,
            @JsonProperty("uuid") String uuid,
            @JsonProperty("version") List<Integer> version,
            @JsonProperty("description") @Nullable String description
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Dependency(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("version") List<Integer> version
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Metadata(
            @JsonProperty("authors") @Nullable List<String> authors,
            @JsonProperty("license") @Nullable String license,
            @JsonProperty("url") @Nullable String url
    ) {}

    // Helper methods
    public String getUuid() {
        return header.uuid();
    }

    public String getName() {
        return header.name();
    }

    public String getVersion() {
        List<Integer> ver = header.version();
        if (ver == null || ver.isEmpty()) return "0.0.0";
        int major = ver.get(0);
        int minor = ver.size() > 1 ? ver.get(1) : 0;
        int patch = ver.size() > 2 ? ver.get(2) : 0;
        return major + "." + minor + "." + patch;
    }

    public boolean isBehaviorPack() {
        return modules != null && modules.stream()
                .anyMatch(m -> "data".equals(m.type()));
    }

    public boolean isResourcePack() {
        return modules != null && modules.stream()
                .anyMatch(m -> "resources".equals(m.type()));
    }
}

