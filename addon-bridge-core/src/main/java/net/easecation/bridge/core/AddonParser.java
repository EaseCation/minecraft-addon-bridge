package net.easecation.bridge.core;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Minimal parser that discovers packs by locating manifest.json under given root.
 * It extracts only uuid/name/version from manifest.json with a naive string search.
 * Other definitions are left empty; resource files are not loaded in this MVP.
 */
public class AddonParser {
    public List<AddonPack> scanAndParse(File addonsRoot) throws IOException {
        List<AddonPack> packs = new ArrayList<>();
        if (addonsRoot == null) return packs;
        if (!addonsRoot.isDirectory()) return packs;

        Files.walk(addonsRoot.toPath())
                .filter(p -> p.getFileName().toString().equalsIgnoreCase("manifest.json"))
                .forEach(manifestPath -> {
                    try {
                        packs.add(parseSingle(manifestPath));
                    } catch (IOException e) {
                        // swallow in minimal impl; real impl should log
                    }
                });
        return packs;
    }

    private AddonPack parseSingle(Path manifestPath) throws IOException {
        String json = Files.readString(manifestPath, StandardCharsets.UTF_8);
        String uuid = extractJsonString(json, "uuid");
        String name = extractJsonString(json, "name");
        String version = extractVersion(json);

        Manifest mf = new Manifest(
                uuid != null ? uuid : "00000000-0000-0000-0000-000000000000",
                name != null ? name : manifestPath.getParent().getFileName().toString(),
                version != null ? version : "0.0.0"
        );

        // Minimal MVP: no items/blocks/entities/recipes yet
        List<ItemDef> items = Collections.emptyList();
        List<BlockDef> blocks = Collections.emptyList();
        List<EntityDef> entities = Collections.emptyList();
        List<RecipeDef> recipes = Collections.emptyList();

        Map<String, byte[]> resourceFiles = new HashMap<>();
        // Optional: could read common resource dirs in future iterations

        return new AddonPack(mf, items, blocks, entities, recipes, resourceFiles);
    }

    private static String extractJsonString(String json, String key) {
        // naive: find "key" : "value"
        String needle = "\"" + key + "\"";
        int idx = json.indexOf(needle);
        if (idx < 0) return null;
        int colon = json.indexOf(":", idx);
        if (colon < 0) return null;
        int q1 = json.indexOf('"', colon);
        if (q1 < 0) return null;
        int q2 = json.indexOf('"', q1 + 1);
        if (q2 < 0) return null;
        return json.substring(q1 + 1, q2);
    }

    private static String extractVersion(String json) {
        // try array version: "version": [1, 0, 0] -> 1.0.0
        String needle = "\"version\"";
        int idx = json.indexOf(needle);
        if (idx < 0) return null;
        int colon = json.indexOf(":", idx);
        if (colon < 0) return null;
        int lb = json.indexOf('[', colon);
        int rb = json.indexOf(']', colon);
        if (lb >= 0 && rb > lb) {
            String inside = json.substring(lb + 1, rb).replaceAll("\n|\r|\s", "");
            String[] parts = inside.split(",");
            if (parts.length >= 1) {
                String a = parts[0];
                String b = parts.length > 1 ? parts[1] : "0";
                String c = parts.length > 2 ? parts[2] : "0";
                return a + "." + b + "." + c;
            }
        }
        // fallback to string version
        return extractJsonString(json, "version");
    }
}

