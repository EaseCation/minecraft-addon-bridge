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

    private static String normalize(String path) {
        return path.replace('\\', '/').replaceAll("^/+", "");
    }
}

