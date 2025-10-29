package net.easecation.bridge.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.BlockDefinitions;
import net.easecation.bridge.core.dto.v1_21_60.behavior.entities.Entity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Enhanced parser that discovers and parses addon packs from directories and ZIP files.
 * Supports .zip, .mcpack, and .mcaddon formats.
 * Uses Jackson for JSON deserialization and reads blocks/entities from behavior packs.
 */
public class AddonParser {
    private static final ObjectMapper MAPPER = createObjectMapper();
    private final BridgeLogger log;

    public AddonParser(BridgeLogger log) {
        this.log = log != null ? log : new BridgeLogger() {
            @Override public void info(String msg) { System.out.println("[INFO] " + msg); }
            @Override public void warning(String msg) { System.out.println("[WARN] " + msg); }
            @Override public void error(String msg) { System.err.println("[ERROR] " + msg); }
            @Override public void debug(String msg) { System.out.println("[DEBUG] " + msg); }
            @Override public void trace(String msg) { System.out.println("[TRACE] " + msg); }
        };
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public List<AddonPack> scanAndParse(File addonsRoot) throws IOException {
        log.debug("Scanning directory: " + (addonsRoot != null ? addonsRoot.getAbsolutePath() : "null"));

        List<AddonPack> packs = new ArrayList<>();
        if (addonsRoot == null) {
            log.debug("addonsRoot is null, returning empty list");
            return packs;
        }
        if (!addonsRoot.isDirectory()) {
            log.debug("addonsRoot is not a directory, returning empty list");
            return packs;
        }

        log.debug("Directory exists: " + addonsRoot.exists());
        log.debug("Is directory: " + addonsRoot.isDirectory());

        // Scan for ZIP files (.zip, .mcpack, .mcaddon)
        List<File> zipFiles = Files.walk(addonsRoot.toPath(), 1)
                .filter(p -> p.toFile().isFile())
                .map(Path::toFile)
                .filter(f -> {
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".zip") || name.endsWith(".mcpack") || name.endsWith(".mcaddon");
                })
                .sorted(Comparator.comparing(File::getName))
                .collect(Collectors.toList());

        log.debug("Found " + zipFiles.size() + " ZIP/MCPACK/MCADDON files");
        for (File zipFile : zipFiles) {
            log.debug("  - " + zipFile.getName());
        }

        // Parse each ZIP file
        for (File zipFile : zipFiles) {
            try {
                log.debug("Parsing ZIP: " + zipFile.getName());
                AddonPack pack = parseZipPack(zipFile);
                boolean isBehavior = pack.manifest().isBehaviorPack();
                log.debug("  Pack name: " + pack.manifest().getName());
                log.debug("  Is behavior pack: " + isBehavior);
                log.debug("  Blocks: " + pack.blocks().size());
                log.debug("  Entities: " + pack.entities().size());
                log.debug("  Items: " + pack.items().size());
                packs.add(pack);
            } catch (Exception e) {
                log.error("Failed to parse pack: " + zipFile.getName());
                log.error("  Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                e.printStackTrace();
                // Continue with next pack
            }
        }

        // Also scan for directory-based packs
        log.debug("Scanning for directory-based packs...");
        final int[] dirPackCount = {0};
        Files.walk(addonsRoot.toPath())
                .filter(p -> p.getFileName().toString().equalsIgnoreCase("manifest.json"))
                .filter(p -> !isInsideZip(p))
                .forEach(manifestPath -> {
                    dirPackCount[0]++;
                    log.debug("Found directory pack manifest: " + manifestPath);
                    try {
                        AddonPack pack = parseDirectoryPack(manifestPath.getParent());
                        log.debug("  Pack name: " + pack.manifest().getName());
                        log.debug("  Is behavior pack: " + pack.manifest().isBehaviorPack());
                        log.debug("  Blocks: " + pack.blocks().size());
                        packs.add(pack);
                    } catch (IOException e) {
                        log.error("Failed to parse directory pack: " + manifestPath.getParent());
                        log.error("  Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
        log.debug("Found " + dirPackCount[0] + " directory pack(s)");

        log.info("Total packs loaded: " + packs.size());
        return packs;
    }

    private boolean isInsideZip(Path path) {
        return path.toString().contains(".zip") || path.toString().contains(".mcpack");
    }

    private AddonPack parseZipPack(File zipFile) throws IOException {
        try (ZipFile zip = new ZipFile(zipFile)) {
            // Read manifest.json
            ZipEntry manifestEntry = zip.getEntry("manifest.json");
            if (manifestEntry == null) {
                throw new IOException("No manifest.json found in " + zipFile.getName());
            }

            Manifest manifest;
            try (InputStream is = zip.getInputStream(manifestEntry)) {
                manifest = MAPPER.readValue(is, Manifest.class);
            }

            // For ZIP/MCPACK files, we don't need to package them again
            Path originalPath = zipFile.toPath();
            boolean needsPackaging = false;

            // Only parse behavior packs (data modules)
            if (!manifest.isBehaviorPack()) {
                return new AddonPack(manifest, List.of(), List.of(), List.of(), List.of(), originalPath, needsPackaging);
            }

            // Parse blocks
            List<BlockDef> blocks = new ArrayList<>();
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith("blocks/") && name.endsWith(".json")) {
                    try (InputStream is = zip.getInputStream(entry)) {
                        String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                        // Parse wrapper: {"format_version": "1.21.60", "minecraft:block": {...}}
                        Map<String, Object> wrapper = MAPPER.readValue(json, Map.class);
                        if (wrapper.containsKey("minecraft:block")) {
                            Object blockData = wrapper.get("minecraft:block");
                            BlockDefinitions blockDef = MAPPER.convertValue(blockData, BlockDefinitions.class);
                            blocks.add(BlockDef.fromDTO(blockDef));
                        }
                    } catch (Exception e) {
                        log.debug("Failed to parse block: " + name + " - " + e.getMessage());
                    }
                }
            }

            // Parse entities
            List<EntityDef> entities = new ArrayList<>();
            entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith("entities/") && name.endsWith(".json")) {
                    try (InputStream is = zip.getInputStream(entry)) {
                        String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                        // Parse wrapper: {"format_version": "1.21.60", "minecraft:entity": {...}}
                        Map<String, Object> wrapper = MAPPER.readValue(json, Map.class);
                        if (wrapper.containsKey("minecraft:entity")) {
                            Object entityData = wrapper.get("minecraft:entity");
                            Entity entityDto = MAPPER.convertValue(entityData, Entity.class);
                            entities.add(EntityDef.fromDTO(entityDto));
                        }
                    } catch (Exception e) {
                        log.debug("Failed to parse entity: " + name + " - " + e.getMessage());
                    }
                }
            }

            // Items and recipes not implemented yet
            List<ItemDef> items = List.of();
            List<RecipeDef> recipes = List.of();

            return new AddonPack(manifest, items, blocks, entities, recipes, originalPath, needsPackaging);
        }
    }

    private AddonPack parseDirectoryPack(Path packRoot) throws IOException {
        // Read manifest.json
        Path manifestPath = packRoot.resolve("manifest.json");
        Manifest manifest = MAPPER.readValue(manifestPath.toFile(), Manifest.class);

        // Directory packs need to be packaged
        Path originalPath = packRoot;
        boolean needsPackaging = true;

        // Resource files are NOT loaded into memory
        // They will be streamed directly during packaging to avoid memory overhead

        // Only parse behavior packs
        if (!manifest.isBehaviorPack()) {
            return new AddonPack(manifest, List.of(), List.of(), List.of(), List.of(), originalPath, needsPackaging);
        }

        // Parse blocks
        List<BlockDef> blocks = new ArrayList<>();
        Path blocksDir = packRoot.resolve("blocks");
        if (Files.isDirectory(blocksDir)) {
            Files.walk(blocksDir)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(blockFile -> {
                        try {
                            String json = Files.readString(blockFile, StandardCharsets.UTF_8);
                            Map<String, Object> wrapper = MAPPER.readValue(json, Map.class);
                            if (wrapper.containsKey("minecraft:block")) {
                                Object blockData = wrapper.get("minecraft:block");
                                BlockDefinitions blockDef = MAPPER.convertValue(blockData, BlockDefinitions.class);
                                blocks.add(BlockDef.fromDTO(blockDef));
                            }
                        } catch (Exception e) {
                            log.debug("Failed to parse block: " + blockFile + " - " + e.getMessage());
                        }
                    });
        }

        // Parse entities
        List<EntityDef> entities = new ArrayList<>();
        Path entitiesDir = packRoot.resolve("entities");
        if (Files.isDirectory(entitiesDir)) {
            Files.walk(entitiesDir)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(entityFile -> {
                        try {
                            String json = Files.readString(entityFile, StandardCharsets.UTF_8);
                            Map<String, Object> wrapper = MAPPER.readValue(json, Map.class);
                            if (wrapper.containsKey("minecraft:entity")) {
                                Object entityData = wrapper.get("minecraft:entity");
                                Entity entityDto = MAPPER.convertValue(entityData, Entity.class);
                                entities.add(EntityDef.fromDTO(entityDto));
                            }
                        } catch (Exception e) {
                            log.debug("Failed to parse entity: " + entityFile + " - " + e.getMessage());
                        }
                    });
        }

        // Items and recipes not implemented yet
        List<ItemDef> items = List.of();
        List<RecipeDef> recipes = List.of();

        return new AddonPack(manifest, items, blocks, entities, recipes, originalPath, needsPackaging);
    }
}

