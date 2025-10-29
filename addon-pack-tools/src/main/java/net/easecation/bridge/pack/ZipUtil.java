package net.easecation.bridge.pack;

import java.io.IOException;
import java.io.OutputStream;
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
     * @param sourceDir The source directory to zip
     * @param outZip The output ZIP file path
     * @throws IOException If an I/O error occurs
     */
    public static void zipFromDirectory(Path sourceDir, Path outZip) throws IOException {
        Files.createDirectories(outZip.getParent());

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

                            // Create ZIP entry
                            ZipEntry entry = new ZipEntry(normalizedPath);
                            zos.putNextEntry(entry);

                            // Stream copy: reads and writes in chunks, no memory buffering
                            Files.copy(filePath, zos);

                            zos.closeEntry();
                        } catch (IOException e) {
                            throw new java.io.UncheckedIOException("Failed to zip file: " + filePath, e);
                        }
                    });
        }
    }

    private static String normalize(String path) {
        return path.replace('\\', '/').replaceAll("^/+", "");
    }
}

