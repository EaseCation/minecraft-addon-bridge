package net.easecation.bridge.core.versioned;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Minecraft Bedrock format_version (e.g., "1.19.0", "1.21.60").
 * Provides version comparison and package name conversion.
 */
public record FormatVersion(int major, int minor, int patch) implements Comparable<FormatVersion> {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)(?:\\.(\\d+))?");

    /**
     * Parse format_version string (e.g., "1.21.60" or "1.21").
     */
    @Nullable
    public static FormatVersion parse(String versionString) {
        if (versionString == null || versionString.isEmpty()) {
            return null;
        }

        Matcher matcher = VERSION_PATTERN.matcher(versionString);
        if (!matcher.matches()) {
            return null;
        }

        int major = Integer.parseInt(matcher.group(1));
        int minor = Integer.parseInt(matcher.group(2));
        int patch = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;

        return new FormatVersion(major, minor, patch);
    }

    /**
     * Convert to package name segment (e.g., "v1_21_60").
     */
    public String toPackageName() {
        return "v" + major + "_" + minor + "_" + patch;
    }

    /**
     * Convert to string (e.g., "1.21.60").
     */
    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }

    @Override
    public int compareTo(FormatVersion other) {
        if (this.major != other.major) {
            return Integer.compare(this.major, other.major);
        }
        if (this.minor != other.minor) {
            return Integer.compare(this.minor, other.minor);
        }
        return Integer.compare(this.patch, other.patch);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FormatVersion other)) return false;
        return major == other.major && minor == other.minor && patch == other.patch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }

    // Common versions as constants
    public static final FormatVersion V1_19_0 = new FormatVersion(1, 19, 0);
    public static final FormatVersion V1_19_40 = new FormatVersion(1, 19, 40);
    public static final FormatVersion V1_19_80 = new FormatVersion(1, 19, 80);
    public static final FormatVersion V1_20_0 = new FormatVersion(1, 20, 0);
    public static final FormatVersion V1_20_41 = new FormatVersion(1, 20, 41);
    public static final FormatVersion V1_20_81 = new FormatVersion(1, 20, 81);
    public static final FormatVersion V1_21_20 = new FormatVersion(1, 21, 20);
    public static final FormatVersion V1_21_60 = new FormatVersion(1, 21, 60);

    // Default version (latest)
    public static final FormatVersion DEFAULT = V1_21_60;
}
