package net.easecation.bridge.pack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.Manifest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipUtil {
    private ZipUtil() {}

    public static void zipFromMap(Map<String, byte[]> files, Path outZip) throws IOException {
        Files.createDirectories(outZip.getParent());
        try (OutputStream fos = Files.newOutputStream(outZip);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (Map.Entry<String, byte[]> e : files.entrySet()) {
                String name = normalize(e.getKey());
                ZipEntry entry = new ZipEntry(name);
                zos.putNextEntry(entry);
                if (e.getValue() != null) {
                    zos.write(e.getValue());
                }
                zos.closeEntry();
            }
        }
    }

    /**
     * Streams files from a directory into a ZIP archive without loading them into memory.
     * This method is memory-efficient and suitable for large resource packs.
     *
     * Special handling for manifest.json: validates and fixes missing required fields
     * (name and description in header) to ensure compatibility with Nukkit's ResourcePack loader.
     *
     * @param sourceDir The source directory to zip
     * @param outZip The output ZIP file path
     * @throws IOException If an I/O error occurs
     */
    public static void zipFromDirectory(Path sourceDir, Path outZip) throws IOException {
        Files.createDirectories(outZip.getParent());

        // Configure ObjectMapper to exclude null values from serialization
        // This prevents NullPointerException in Nukkit's ZippedResourcePack parser
        // which expects dependencies/metadata/capabilities to be arrays, not null
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try (OutputStream fos = Files.newOutputStream(outZip);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            // Walk through all files in the directory
            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        try {
                            // Calculate relative path for ZIP entry
                            String relativePath = sourceDir.relativize(filePath).toString();
                            String normalizedPath = normalize(relativePath);

                            // Special handling for manifest.json
                            if (filePath.getFileName().toString().equals("manifest.json")) {
                                handleManifestJson(mapper, sourceDir, filePath, normalizedPath, zos);
                            } else {
                                // Create ZIP entry for regular files
                                ZipEntry entry = new ZipEntry(normalizedPath);
                                zos.putNextEntry(entry);

                                // Stream copy: reads and writes in chunks, no memory buffering
                                Files.copy(filePath, zos);

                                zos.closeEntry();
                            }
                        } catch (IOException e) {
                            throw new java.io.UncheckedIOException("Failed to zip file: " + filePath, e);
                        }
                    });
        }
    }

    /**
     * Handles manifest.json file: validates and fixes missing required fields.
     * Nukkit's ZippedResourcePack requires header.name and header.description to be present.
     */
    private static void handleManifestJson(ObjectMapper mapper, Path sourceDir, Path filePath,
                                          String normalizedPath, ZipOutputStream zos) throws IOException {
        // Read original manifest
        Manifest manifest = mapper.readValue(filePath.toFile(), Manifest.class);

        // Check if header needs fixing
        Manifest.Header header = manifest.header();
        String originalName = header.name();
        String originalDescription = header.description();

        boolean nameNeedsfix = (originalName == null || originalName.isEmpty());
        boolean descNeedsFix = (originalDescription == null);

        if (nameNeedsfix || descNeedsFix) {
            // Determine final values
            String fixedName = nameNeedsfix
                ? sourceDir.getFileName().toString()
                : originalName;

            String fixedDescription = descNeedsFix
                ? "Resource Pack"
                : originalDescription;

            BridgeLoggerHolder.getLogger().info("Fixing manifest.json in " + sourceDir.getFileName());
            BridgeLoggerHolder.getLogger().info("  Original name: " + originalName + " -> Fixed: " + fixedName);
            BridgeLoggerHolder.getLogger().info("  Original description: " + originalDescription + " -> Fixed: " + fixedDescription);

            // Rebuild Header with fixed fields
            Manifest.Header fixedHeader = new Manifest.Header(
                header.uuid(),
                fixedName,
                fixedDescription,
                header.version(),
                header.minEngineVersion()
            );

            // Rebuild complete Manifest
            manifest = new Manifest(
                manifest.formatVersion(),
                fixedHeader,
                manifest.modules(),
                manifest.dependencies(),
                manifest.metadata(),
                manifest.capabilities()
            );
        }

        // Write (fixed) manifest to ZIP with ALWAYS inclusion policy
        String manifestJson = mapper.writerWithDefaultPrettyPrinter()
                                    .writeValueAsString(manifest);

        BridgeLoggerHolder.getLogger().debug("Serialized manifest.json:");
        BridgeLoggerHolder.getLogger().debug(manifestJson);

        ZipEntry entry = new ZipEntry(normalizedPath);
        zos.putNextEntry(entry);
        zos.write(manifestJson.getBytes(StandardCharsets.UTF_8));
        zos.closeEntry();
    }

    private static String normalize(String path) {
        return path.replace('\\', '/').replaceAll("^/+", "");
    }
}

