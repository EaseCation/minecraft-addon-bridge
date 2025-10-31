package net.easecation.bridge.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.easecation.bridge.core.dto.block.v1_21_60.BlockDefinitions;
import net.easecation.bridge.core.dto.entity.v1_21_60.Entity;
import net.easecation.bridge.core.dto.item.v1_21_60.Item;
import net.easecation.bridge.core.versioned.VersionedDtoLoader;

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
    private final VersionedDtoLoader dtoLoader = new VersionedDtoLoader(MAPPER);

    public AddonParser() {
        // Logger通过BridgeLoggerHolder全局访问，无需传递
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public List<AddonPack> scanAndParse(File addonsRoot) throws IOException {
        BridgeLoggerHolder.getLogger().debug("Scanning directory: " + (addonsRoot != null ? addonsRoot.getAbsolutePath() : "null"));

        List<AddonPack> packs = new ArrayList<>();
        if (addonsRoot == null) {
            BridgeLoggerHolder.getLogger().debug("addonsRoot is null, returning empty list");
            return packs;
        }
        if (!addonsRoot.isDirectory()) {
            BridgeLoggerHolder.getLogger().debug("addonsRoot is not a directory, returning empty list");
            return packs;
        }

        BridgeLoggerHolder.getLogger().debug("Directory exists: " + addonsRoot.exists());
        BridgeLoggerHolder.getLogger().debug("Is directory: " + addonsRoot.isDirectory());

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

        BridgeLoggerHolder.getLogger().debug("Found " + zipFiles.size() + " ZIP/MCPACK/MCADDON files");
        for (File zipFile : zipFiles) {
            BridgeLoggerHolder.getLogger().debug("  - " + zipFile.getName());
        }

        // Parse each ZIP file
        for (File zipFile : zipFiles) {
            try {
                BridgeLoggerHolder.getLogger().debug("Parsing ZIP: " + zipFile.getName());
                AddonPack pack = parseZipPack(zipFile);
                boolean isBehavior = pack.manifest().isBehaviorPack();
                BridgeLoggerHolder.getLogger().debug("  Pack name: " + pack.packName());
                BridgeLoggerHolder.getLogger().debug("  Is behavior pack: " + isBehavior);
                BridgeLoggerHolder.getLogger().debug("  Blocks: " + pack.blocks().size());
                BridgeLoggerHolder.getLogger().debug("  Entities: " + pack.entities().size());
                BridgeLoggerHolder.getLogger().debug("  Items: " + pack.items().size());
                packs.add(pack);
            } catch (Exception e) {
                BridgeLoggerHolder.getLogger().error("Failed to parse pack: " + zipFile.getName());
                BridgeLoggerHolder.getLogger().error("  Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                e.printStackTrace();
                // Continue with next pack
            }
        }

        // Also scan for directory-based packs
        BridgeLoggerHolder.getLogger().debug("Scanning for directory-based packs...");
        final int[] dirPackCount = {0};
        Files.walk(addonsRoot.toPath())
                .filter(p -> p.getFileName().toString().equalsIgnoreCase("manifest.json"))
                .filter(p -> !isInsideZip(p))
                .forEach(manifestPath -> {
                    dirPackCount[0]++;
                    BridgeLoggerHolder.getLogger().debug("Found directory pack manifest: " + manifestPath);
                    try {
                        AddonPack pack = parseDirectoryPack(manifestPath.getParent());
                        BridgeLoggerHolder.getLogger().debug("  Pack name: " + pack.packName());
                        BridgeLoggerHolder.getLogger().debug("  Is behavior pack: " + pack.manifest().isBehaviorPack());
                        BridgeLoggerHolder.getLogger().debug("  Blocks: " + pack.blocks().size());
                        packs.add(pack);
                    } catch (IOException e) {
                        BridgeLoggerHolder.getLogger().error("Failed to parse directory pack: " + manifestPath.getParent());
                        BridgeLoggerHolder.getLogger().error("  Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
        BridgeLoggerHolder.getLogger().debug("Found " + dirPackCount[0] + " directory pack(s)");

        BridgeLoggerHolder.getLogger().info("Total packs loaded: " + packs.size());
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

            // Extract pack name from file name (without extension)
            String fileName = zipFile.getName();
            String packName = fileName;
            if (fileName.endsWith(".mcpack") || fileName.endsWith(".zip")) {
                int dotIndex = fileName.lastIndexOf('.');
                packName = fileName.substring(0, dotIndex);
            }

            // For ZIP/MCPACK files, we don't need to package them again
            Path originalPath = zipFile.toPath();
            boolean needsPackaging = false;

            // Only parse behavior packs (data modules)
            if (!manifest.isBehaviorPack()) {
                return new AddonPack(manifest, List.of(), List.of(), List.of(), List.of(), originalPath, needsPackaging, packName);
            }

            // Parse blocks (including Netease edition blocks)
            List<BlockDef> blocks = new ArrayList<>();
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if ((name.startsWith("blocks/") || name.startsWith("netease_blocks/")) && name.endsWith(".json")) {
                    try (InputStream is = zip.getInputStream(entry)) {
                        String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                        // Parse wrapper: {"format_version": "1.21.60", "minecraft:block": {...}}
                        Map<String, Object> wrapper = MAPPER.readValue(json, Map.class);
                        if (wrapper.containsKey("minecraft:block")) {
                            String formatVersion = (String) wrapper.get("format_version");
                            Object blockData = wrapper.get("minecraft:block");

                            // Load and upgrade to latest version
                            BlockDefinitions blockDef = dtoLoader.loadBlock(blockData, formatVersion);
                            blocks.add(BlockDef.fromDTO(blockDef));
                        }
                    } catch (Exception e) {
                        BridgeLoggerHolder.getLogger().debug("Failed to parse block: " + name + " - " + e.getMessage());
                    }
                }
            }

            // Parse entities
            List<EntityDef> entities = new ArrayList<>();
            entries = zip.entries();
            int entityFileCount = 0;
            int entityParsedCount = 0;
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith("entities/") && name.endsWith(".json")) {
                    entityFileCount++;
                    try (InputStream is = zip.getInputStream(entry)) {
                        String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                        // Parse wrapper: {"format_version": "1.21.60", "minecraft:entity": {...}}
                        Map<String, Object> wrapper = MAPPER.readValue(json, Map.class);
                        if (wrapper.containsKey("minecraft:entity")) {
                            String formatVersion = (String) wrapper.get("format_version");
                            Object entityData = wrapper.get("minecraft:entity");

                            // Load and upgrade to latest version
                            Entity entityDto = dtoLoader.loadEntity(entityData, formatVersion);
                            try {
                                String entityJson = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(entityDto);
                                BridgeLoggerHolder.getLogger().debug(String.format("Final upgraded Entity DTO as JSON:\n%s", entityJson));
                            } catch (Exception e) {
                                BridgeLoggerHolder.getLogger().warning("Failed to serialize Entity DTO to JSON: " + e.getMessage());
                            }
                            entities.add(EntityDef.fromDTO(entityDto));
                            entityParsedCount++;
                        } else {
                            BridgeLoggerHolder.getLogger().warning("Entity file missing 'minecraft:entity' key: " + name);
                        }
                    } catch (Exception e) {
                        BridgeLoggerHolder.getLogger().error("Failed to parse entity: " + name);
                        BridgeLoggerHolder.getLogger().error("  Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                        if (e.getCause() != null) {
                            BridgeLoggerHolder.getLogger().error("  Cause: " + e.getCause().getClass().getSimpleName() + " - " + e.getCause().getMessage());
                        }
                    }
                }
            }
            if (entityFileCount > 0) {
                BridgeLoggerHolder.getLogger().info("Entity parsing: found " + entityFileCount + " files, successfully parsed " + entityParsedCount);
            }

            // Parse items (including Netease edition items)
            List<ItemDef> items = new ArrayList<>();
            entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if ((name.startsWith("items/") || name.startsWith("netease_items_beh/")) && name.endsWith(".json")) {
                    try (InputStream is = zip.getInputStream(entry)) {
                        String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                        // Parse as Map to extract format_version
                        Map<String, Object> itemMap = MAPPER.readValue(json, Map.class);
                        String formatVersion = (String) itemMap.get("format_version");

                        // **关键分支：根据format_version判断模式**
                        ItemDef itemDef;
                        if (formatVersion != null && formatVersion.startsWith("1.10")) {
                            // Legacy模式（1.10）：直接加载v1_10 DTO
                            net.easecation.bridge.core.dto.item.v1_10.ItemsDefinition legacyDto =
                                MAPPER.readValue(json, net.easecation.bridge.core.dto.item.v1_10.ItemsDefinition.class);

                            itemDef = ItemDef.fromLegacyDTO(legacyDto.minecraft_item());
                        } else {
                            // Component-based模式（1.19+）：现有升级流程
                            net.easecation.bridge.core.dto.item.v1_21_60.ItemsDefinition itemsDef =
                                dtoLoader.loadItem(itemMap, formatVersion);

                            itemDef = ItemDef.fromComponentBasedDTO(itemsDef.minecraft_item());
                        }

                        items.add(itemDef);
                    } catch (Exception e) {
                        BridgeLoggerHolder.getLogger().debug("Failed to parse item: " + name + " - " + e.getMessage());
                    }
                }
            }

            // Recipes not implemented yet
            List<RecipeDef> recipes = List.of();

            return new AddonPack(manifest, items, blocks, entities, recipes, originalPath, needsPackaging, packName);
        }
    }

    private AddonPack parseDirectoryPack(Path packRoot) throws IOException {
        // Read manifest.json
        Path manifestPath = packRoot.resolve("manifest.json");
        Manifest manifest = MAPPER.readValue(manifestPath.toFile(), Manifest.class);

        // Extract pack name from directory name
        String packName = packRoot.getFileName().toString();

        // Directory packs need to be packaged
        Path originalPath = packRoot;
        boolean needsPackaging = true;

        // Resource files are NOT loaded into memory
        // They will be streamed directly during packaging to avoid memory overhead

        // Only parse behavior packs
        if (!manifest.isBehaviorPack()) {
            return new AddonPack(manifest, List.of(), List.of(), List.of(), List.of(), originalPath, needsPackaging, packName);
        }

        // Parse blocks (including Netease edition blocks)
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
                                String formatVersion = (String) wrapper.get("format_version");
                                Object blockData = wrapper.get("minecraft:block");

                                // Load and upgrade to latest version
                                BlockDefinitions blockDef = dtoLoader.loadBlock(blockData, formatVersion);
                                blocks.add(BlockDef.fromDTO(blockDef));
                            }
                        } catch (Exception e) {
                            BridgeLoggerHolder.getLogger().debug("Failed to parse block: " + blockFile + " - " + e.getMessage());
                        }
                    });
        }
        // Also parse Netease edition blocks
        Path neteaseBlocksDir = packRoot.resolve("netease_blocks");
        if (Files.isDirectory(neteaseBlocksDir)) {
            Files.walk(neteaseBlocksDir)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(blockFile -> {
                        try {
                            String json = Files.readString(blockFile, StandardCharsets.UTF_8);
                            Map<String, Object> wrapper = MAPPER.readValue(json, Map.class);
                            if (wrapper.containsKey("minecraft:block")) {
                                String formatVersion = (String) wrapper.get("format_version");
                                Object blockData = wrapper.get("minecraft:block");

                                // Load and upgrade to latest version
                                BlockDefinitions blockDef = dtoLoader.loadBlock(blockData, formatVersion);
                                blocks.add(BlockDef.fromDTO(blockDef));
                            }
                        } catch (Exception e) {
                            BridgeLoggerHolder.getLogger().debug("Failed to parse Netease block: " + blockFile + " - " + e.getMessage());
                        }
                    });
        }

        // Parse entities
        List<EntityDef> entities = new ArrayList<>();
        Path entitiesDir = packRoot.resolve("entities");
        if (Files.isDirectory(entitiesDir)) {
            final int[] entityFileCount = {0};
            final int[] entityParsedCount = {0};
            Files.walk(entitiesDir)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(entityFile -> {
                        entityFileCount[0]++;
                        try {
                            String json = Files.readString(entityFile, StandardCharsets.UTF_8);
                            Map<String, Object> wrapper = MAPPER.readValue(json, Map.class);
                            if (wrapper.containsKey("minecraft:entity")) {
                                String formatVersion = (String) wrapper.get("format_version");
                                Object entityData = wrapper.get("minecraft:entity");

                                // Load and upgrade to latest version
                                Entity entityDto = dtoLoader.loadEntity(entityData, formatVersion);
                                try {
                                    String entityJson = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(entityDto);
                                    BridgeLoggerHolder.getLogger().debug(String.format("Final upgraded Entity DTO as JSON:\n%s", entityJson));
                                } catch (Exception e) {
                                    BridgeLoggerHolder.getLogger().warning("Failed to serialize Entity DTO to JSON: " + e.getMessage());
                                }
                                entities.add(EntityDef.fromDTO(entityDto));
                                entityParsedCount[0]++;
                            } else {
                                BridgeLoggerHolder.getLogger().warning("Entity file missing 'minecraft:entity' key: " + entityFile);
                            }
                        } catch (Exception e) {
                            BridgeLoggerHolder.getLogger().error("Failed to parse entity: " + entityFile);
                            BridgeLoggerHolder.getLogger().error("  Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                            if (e.getCause() != null) {
                                BridgeLoggerHolder.getLogger().error("  Cause: " + e.getCause().getClass().getSimpleName() + " - " + e.getCause().getMessage());
                            }
                        }
                    });
            if (entityFileCount[0] > 0) {
                BridgeLoggerHolder.getLogger().info("Entity parsing: found " + entityFileCount[0] + " files, successfully parsed " + entityParsedCount[0]);
            }
        }

        // Parse items (including Netease edition items)
        List<ItemDef> items = new ArrayList<>();
        Path itemsDir = packRoot.resolve("items");
        if (Files.isDirectory(itemsDir)) {
            Files.walk(itemsDir)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(itemFile -> {
                        try {
                            String json = Files.readString(itemFile, StandardCharsets.UTF_8);

                            // Parse as Map to extract format_version
                            Map<String, Object> itemMap = MAPPER.readValue(json, Map.class);
                            String formatVersion = (String) itemMap.get("format_version");

                            // **关键分支：根据format_version判断模式**
                            ItemDef itemDef;
                            if (formatVersion != null && formatVersion.startsWith("1.10")) {
                                // Legacy模式（1.10）：直接加载v1_10 DTO
                                net.easecation.bridge.core.dto.item.v1_10.ItemsDefinition legacyDto =
                                    MAPPER.readValue(json, net.easecation.bridge.core.dto.item.v1_10.ItemsDefinition.class);

                                itemDef = ItemDef.fromLegacyDTO(legacyDto.minecraft_item());
                            } else {
                                // Component-based模式（1.19+）：现有升级流程
                                net.easecation.bridge.core.dto.item.v1_21_60.ItemsDefinition itemsDef =
                                    dtoLoader.loadItem(itemMap, formatVersion);

                                itemDef = ItemDef.fromComponentBasedDTO(itemsDef.minecraft_item());
                            }

                            items.add(itemDef);
                        } catch (Exception e) {
                            BridgeLoggerHolder.getLogger().debug("Failed to parse item: " + itemFile + " - " + e.getMessage());
                        }
                    });
        }
        // Also parse Netease edition items
        Path neteaseItemsDir = packRoot.resolve("netease_items_beh");
        if (Files.isDirectory(neteaseItemsDir)) {
            Files.walk(neteaseItemsDir)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(itemFile -> {
                        try {
                            String json = Files.readString(itemFile, StandardCharsets.UTF_8);

                            // Parse as Map to extract format_version
                            Map<String, Object> itemMap = MAPPER.readValue(json, Map.class);
                            String formatVersion = (String) itemMap.get("format_version");

                            // **关键分支：根据format_version判断模式**
                            ItemDef itemDef;
                            if (formatVersion != null && formatVersion.startsWith("1.10")) {
                                // Legacy模式（1.10）：直接加载v1_10 DTO
                                net.easecation.bridge.core.dto.item.v1_10.ItemsDefinition legacyDto =
                                    MAPPER.readValue(json, net.easecation.bridge.core.dto.item.v1_10.ItemsDefinition.class);

                                itemDef = ItemDef.fromLegacyDTO(legacyDto.minecraft_item());
                            } else {
                                // Component-based模式（1.19+）：现有升级流程
                                net.easecation.bridge.core.dto.item.v1_21_60.ItemsDefinition itemsDef =
                                    dtoLoader.loadItem(itemMap, formatVersion);

                                itemDef = ItemDef.fromComponentBasedDTO(itemsDef.minecraft_item());
                            }

                            items.add(itemDef);
                        } catch (Exception e) {
                            BridgeLoggerHolder.getLogger().debug("Failed to parse Netease item: " + itemFile + " - " + e.getMessage());
                        }
                    });
        }

        // Recipes not implemented yet
        List<RecipeDef> recipes = List.of();

        return new AddonPack(manifest, items, blocks, entities, recipes, originalPath, needsPackaging, packName);
    }
}

