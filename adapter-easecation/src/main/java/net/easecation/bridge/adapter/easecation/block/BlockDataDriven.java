package net.easecation.bridge.adapter.easecation.block;

import cn.nukkit.block.Block;
import cn.nukkit.block.CustomBlock;
import cn.nukkit.block.state.BlockStates;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.SimpleAxisAlignedBB;
import net.easecation.bridge.core.BlockDef;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Component;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Geometry;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.CollisionBox;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.StatesValue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data-driven custom block implementation for EaseCation Nukkit.
 * Based on ECProEntity's BlockDataDriven implementation.
 */
public class BlockDataDriven extends CustomBlock {
    private static final SimpleAxisAlignedBB CUBE = new SimpleAxisAlignedBB(0, 0, 0, 1, 1, 1);

    // Static registry to map runtime ID to BlockDef
    private static final Map<Integer, BlockDef> BLOCK_DEF_REGISTRY = new ConcurrentHashMap<>();

    private BlockDefinition definition;

    public BlockDataDriven(int id) {
        super(id);
        tryInitBlockDefinition();
    }

    /**
     * Register a BlockDef for a given runtime ID.
     */
    public static void registerBlockDef(int runtimeId, BlockDef blockDef) {
        BLOCK_DEF_REGISTRY.put(runtimeId, blockDef);
    }

    protected void tryInitBlockDefinition() {
        if (this.definition != null) {
            return;
        }

        int runtimeId = this.getId();
        BlockDef blockDef = BLOCK_DEF_REGISTRY.get(runtimeId);
        if (blockDef == null) {
            throw new RuntimeException("BlockDataDriven: BlockDef not found for runtimeId " + runtimeId +
                ". Registry size: " + BLOCK_DEF_REGISTRY.size() +
                ". Available IDs: " + BLOCK_DEF_REGISTRY.keySet());
        }

        String blockId = blockDef.id();
        try {
            // Debug: Log block definition initialization
            // System.out.println("[BlockDataDriven] Initializing block definition for: " + blockId + " (runtimeId=" + runtimeId + ")");

            Component components = blockDef.components();
        if (components == null) {
            // Minimal block with default properties
            boolean[] traitStates = extractTraitStates(blockDef.description() != null ? blockDef.description().traits() : null);
            this.definition = new BlockDefinition(
                    blockDef.id(), // identifier
                    blockDef.id(), // displayName
                    CUBE, // collisionBox
                    CUBE, // selectionBox
                    0x000000, // mapColor
                    true, // solid
                    false, // replaceable
                    null, // geometryIdentifier
                    true, // unitCube
                    0.0f, // hardness
                    0.0f, // resistance
                    0, // flameOdds
                    0, // burnOdds
                    0.6f, // friction
                    0, // lightEmission
                    15, // lightDampening
                    0b0011, // movable
                    false, // pottable
                    false, // placementFilter
                    0b111111, // allowedPlacementFaces
                    traitStates[0], // stateCardinalDirection
                    traitStates[1], // stateFacingDirection
                    traitStates[2], // stateBlockFace
                    traitStates[3], // stateVerticalHalf
                    traitStates[4], // stateCardinalConnections
                    false, // hasCraftingTable
                    false, // hasTickComponent
                    null, // lootTable
                    false, // hasCustomComponents
                    false, // waterloggable
                    true, // breathable
                    false, // redstoneConductor
                    0, // redstonePower
                    false, // requiresCorrectTool
                    null, // preferredTool
                    0  // toolTier
            );
            return;
        }

        // Extract all properties from components
        SimpleAxisAlignedBB collisionBox = extractCollisionBox(components);
        SimpleAxisAlignedBB selectionBox = extractSelectionBox(components, collisionBox);
        int mapColor = extractMapColor(components);
        boolean solid = extractSolid(components);
        boolean replaceable = extractReplaceable(components);
        String geometryIdentifier = extractGeometryIdentifier(components);
        boolean unitCube = extractUnitCube(components, geometryIdentifier);
        float hardness = extractHardness(components);
        float resistance = extractResistance(components, hardness);
        int flameOdds = extractFlameOdds(components);
        int burnOdds = extractBurnOdds(components);
        float friction = extractFriction(components);
        int lightEmission = extractLightEmission(components);
        int lightDampening = extractLightDampening(components);
        int movable = extractMovable(components);
        boolean pottable = extractPottable(components);
        boolean placementFilter = extractPlacementFilter(components);
        int allowedPlacementFaces = extractAllowedPlacementFaces(components);
        String displayName = extractDisplayName(components, blockDef.id());

        // Extract functional components
        boolean hasCraftingTable = extractHasCraftingTable(components);
        boolean hasTickComponent = extractHasTickComponent(components);
        String lootTable = extractLootTable(components);
        boolean hasCustomComponents = extractHasCustomComponents(components);

        // Extract liquid and environment properties
        boolean waterloggable = extractWaterloggable(components);
        boolean breathable = extractBreathable(components);

        // Extract redstone properties
        boolean redstoneConductor = extractRedstoneConductor(components);
        int redstonePower = extractRedstonePower(components);

        // Extract tool properties
        boolean requiresCorrectTool = extractRequiresCorrectTool(components);
        String preferredTool = extractPreferredTool(components);
        int toolTier = extractToolTier(components);

        // Extract trait states from description
        boolean[] traitStates = extractTraitStates(blockDef.description() != null ? blockDef.description().traits() : null);

        this.definition = new BlockDefinition(
                blockDef.id(), // identifier
                displayName, // displayName
                collisionBox, // collisionBox
                selectionBox, // selectionBox
                mapColor, // mapColor
                solid, // solid
                replaceable, // replaceable
                geometryIdentifier, // geometryIdentifier
                unitCube, // unitCube
                hardness, // hardness
                resistance, // resistance
                flameOdds, // flameOdds
                burnOdds, // burnOdds
                friction, // friction
                lightEmission, // lightEmission
                lightDampening, // lightDampening
                movable, // movable
                pottable, // pottable
                placementFilter, // placementFilter
                allowedPlacementFaces, // allowedPlacementFaces
                traitStates[0], // stateCardinalDirection
                traitStates[1], // stateFacingDirection
                traitStates[2], // stateBlockFace
                traitStates[3], // stateVerticalHalf
                traitStates[4], // stateCardinalConnections
                hasCraftingTable, // hasCraftingTable
                hasTickComponent, // hasTickComponent
                lootTable, // lootTable
                hasCustomComponents, // hasCustomComponents
                waterloggable, // waterloggable
                breathable, // breathable
                redstoneConductor, // redstoneConductor
                redstonePower, // redstonePower
                requiresCorrectTool, // requiresCorrectTool
                preferredTool, // preferredTool
                toolTier  // toolTier
            );

            // Debug: Log successful initialization
            // System.out.println("[BlockDataDriven] Successfully initialized: " + blockId);

        } catch (Exception e) {
            String errorMsg = "Failed to initialize BlockDefinition for block '" + blockId + "' (runtimeId=" + runtimeId + ")";
            System.err.println("[BlockDataDriven] " + errorMsg);
            System.err.println("[BlockDataDriven] Error type: " + e.getClass().getSimpleName());
            System.err.println("[BlockDataDriven] Error message: " + e.getMessage());

            // Print detailed component info for debugging
            if (blockDef.components() != null) {
                System.err.println("[BlockDataDriven] Block has components: YES");
            } else {
                System.err.println("[BlockDataDriven] Block has components: NO");
            }

            e.printStackTrace();
            throw new RuntimeException(errorMsg, e);
        }
    }

    private SimpleAxisAlignedBB extractCollisionBox(Component comp) {
        if (comp.minecraft_collisionBox() == null) {
            return CUBE;
        }

        var collisionBox = comp.minecraft_collisionBox();

        // CollisionBox is a sealed interface with variants
        if (collisionBox instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.CollisionBox.CollisionBox_Variant0 variant0) {
            // Boolean value: true = default cube, false = no collision
            return variant0.value() ? CUBE : null;
        } else if (collisionBox instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.CollisionBox.CollisionBox_Variant1 variant1) {
            // Detailed config with origin and size
            // Origin is specified as [x, y, z] in range (-8, 0, -8) to (8, 16, 8)
            // Size is specified as [x, y, z]

            // Default values based on ECProEntity implementation
            float[] origin = new float[]{-8, 0, -8};
            float[] size = new float[]{16, 16, 16};

            // Parse origin
            if (variant1.origin() != null && variant1.origin().size() >= 3) {
                var originList = variant1.origin();
                origin[0] = originList.get(0).floatValue();
                origin[1] = originList.get(1).floatValue();
                origin[2] = originList.get(2).floatValue();
            }

            // Parse size
            if (variant1.size() != null && variant1.size().size() >= 3) {
                var sizeList = variant1.size();
                size[0] = sizeList.get(0).floatValue();
                size[1] = sizeList.get(1).floatValue();
                size[2] = sizeList.get(2).floatValue();
            }

            // Convert to normalized coordinates (0-1 range)
            // Based on ECProEntity's calculation
            float x0 = (8 + origin[0]) / 16;
            float y0 = origin[1] / 16;
            float z0 = (8 + origin[2]) / 16;

            return new SimpleAxisAlignedBB(
                    x0,
                    y0,
                    z0,
                    x0 + size[0] / 16,
                    y0 + size[1] / 16,
                    z0 + size[2] / 16
            );
        }

        return CUBE;
    }

    private float extractHardness(Component comp) {
        if (comp.minecraft_destructibleByMining() != null) {
            var destructible = comp.minecraft_destructibleByMining();
            // DestructibleByMining is a sealed interface with variants
            if (destructible instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.DestructibleByMining.DestructibleByMining_Variant0 variant0) {
                // Boolean value: true = default hardness, false = unbreakable
                return variant0.value() ? 1.0f : -1.0f;
            } else if (destructible instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.DestructibleByMining.DestructibleByMining_Variant1 variant1) {
                // Detailed config with seconds_to_destroy
                if (variant1.secondsToDestroy() != null) {
                    return variant1.secondsToDestroy().floatValue();
                }
            }
        }
        return 0.0f;
    }

    private float extractResistance(Component comp, float hardness) {
        if (comp.minecraft_destructibleByExplosion() != null) {
            var explosion = comp.minecraft_destructibleByExplosion();
            // DestructibleByExplosion is a sealed interface with variants
            if (explosion instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.DestructibleByExplosion.DestructibleByExplosion_Variant0 variant0) {
                // Boolean value: true = default resistance, false = immune
                return variant0.value() ? hardness * 5.0f : Float.MAX_VALUE;
            } else if (explosion instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.DestructibleByExplosion.DestructibleByExplosion_Variant1 variant1) {
                // Detailed config with explosion_resistance
                if (variant1.explosionResistance() != null) {
                    return variant1.explosionResistance().floatValue();
                }
            }
        }
        return hardness * 5.0f;
    }

    private int extractLightEmission(Component comp) {
        if (comp.minecraft_lightEmission() != null) {
            return comp.minecraft_lightEmission();
        }
        return 0;
    }

    private int extractLightDampening(Component comp) {
        if (comp.minecraft_lightDampening() != null) {
            return comp.minecraft_lightDampening();
        }
        return 15;
    }

    private float extractFriction(Component comp) {
        if (comp.minecraft_friction() != null) {
            return comp.minecraft_friction().floatValue();
        }
        return 0.6f; // Default friction
    }

    private String extractDisplayName(Component comp, String fallback) {
        if (comp.minecraft_displayName() != null) {
            return comp.minecraft_displayName();
        }
        return fallback;
    }

    private SimpleAxisAlignedBB extractSelectionBox(Component comp, SimpleAxisAlignedBB collisionBox) {
        if (comp.minecraft_selectionBox() == null) {
            return collisionBox; // Default to collision box
        }

        var selectionBox = comp.minecraft_selectionBox();
        if (selectionBox instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.SelectionBox.SelectionBox_Variant0 variant0) {
            return variant0.value() ? CUBE : null;
        } else if (selectionBox instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.SelectionBox.SelectionBox_Variant1 variant1) {
            float[] origin = new float[]{-8, 0, -8};
            float[] size = new float[]{16, 16, 16};

            if (variant1.origin() != null && variant1.origin().size() >= 3) {
                var originList = variant1.origin();
                origin[0] = originList.get(0).floatValue();
                origin[1] = originList.get(1).floatValue();
                origin[2] = originList.get(2).floatValue();
            }

            if (variant1.size() != null && variant1.size().size() >= 3) {
                var sizeList = variant1.size();
                size[0] = sizeList.get(0).floatValue();
                size[1] = sizeList.get(1).floatValue();
                size[2] = sizeList.get(2).floatValue();
            }

            float x0 = (8 + origin[0]) / 16;
            float y0 = origin[1] / 16;
            float z0 = (8 + origin[2]) / 16;

            return new SimpleAxisAlignedBB(
                    x0, y0, z0,
                    x0 + size[0] / 16,
                    y0 + size[1] / 16,
                    z0 + size[2] / 16
            );
        }
        return collisionBox;
    }

    private int extractMapColor(Component comp) {
        // TODO: Parse minecraft:map_color component
        // For now, return black (0x000000)
        return 0x000000;
    }

    private boolean extractSolid(Component comp) {
        // A block is solid if it's breathable air=false and has opaque materials
        // For now, default to true
        return true;
    }

    private boolean extractReplaceable(Component comp) {
        return comp.minecraft_replaceable() != null;
    }

    private int extractFlameOdds(Component comp) {
        if (comp.minecraft_flammable() != null) {
            var flammable = comp.minecraft_flammable();
            if (flammable instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Flammable.Flammable_Variant1 variant1) {
                if (variant1.catchChanceModifier() != null) {
                    return variant1.catchChanceModifier().intValue();
                }
                return 5; // Default catch chance
            } else if (flammable instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Flammable.Flammable_Variant0 variant0) {
                return variant0.value() ? 5 : 0;
            }
        }
        return 0;
    }

    private int extractBurnOdds(Component comp) {
        if (comp.minecraft_flammable() != null) {
            var flammable = comp.minecraft_flammable();
            if (flammable instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Flammable.Flammable_Variant1 variant1) {
                if (variant1.destroyChanceModifier() != null) {
                    return variant1.destroyChanceModifier().intValue();
                }
                return 20; // Default destroy chance
            } else if (flammable instanceof net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Flammable.Flammable_Variant0 variant0) {
                return variant0.value() ? 20 : 0;
            }
        }
        return 0;
    }

    private int extractMovable(Component comp) {
        // TODO: Parse minecraft:movable component
        // Bit field: 0b0011 = push_pull, 0b0001 = push, 0b0101 = popped, 0 = immovable
        return 0b0011; // Default: push_pull
    }

    private boolean extractPottable(Component comp) {
        // TODO: Check for minecraft:flower_pottable component
        return false;
    }

    private boolean extractPlacementFilter(Component comp) {
        return comp.minecraft_placementFilter() != null;
    }

    private int extractAllowedPlacementFaces(Component comp) {
        // TODO: Parse placement filter conditions to extract allowed faces
        // Bit field: 0x1=down, 0x2=up, 0x4=north, 0x8=south, 0x10=west, 0x20=east
        return 0b111111; // Default: all faces
    }

    private boolean[] extractTraitStates(net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.Traits traits) {
        // Returns: [cardinalDirection, facingDirection, blockFace, verticalHalf, cardinalConnections]
        boolean[] states = new boolean[5];
        if (traits == null) {
            return states;
        }

        // Placement direction
        if (traits.minecraft_placementDirection() != null) {
            var enabledStates = traits.minecraft_placementDirection().enabledStates();
            if (enabledStates != null) {
                states[0] = enabledStates.contains("minecraft:cardinal_direction");
                states[1] = enabledStates.contains("minecraft:facing_direction");
            }
        }

        // Placement position
        if (traits.minecraft_placementPosition() != null) {
            var enabledStates = traits.minecraft_placementPosition().enabledStates();
            if (enabledStates != null) {
                states[2] = enabledStates.contains("minecraft:block_face");
                states[3] = enabledStates.contains("minecraft:vertical_half");
            }
        }

        // Connection trait (for fence-like blocks)
        // Note: minecraft:connection trait not yet in DTO schema
        // If needed, it would be checked here:
        // if (traits.minecraft_connection() != null) { ... }
        // For now, cardinalConnections (states[4]) remains false

        return states;
    }

    private String extractGeometryIdentifier(Component comp) {
        if (comp.minecraft_geometry() == null) {
            return null;
        }
        var geometry = comp.minecraft_geometry();
        if (geometry instanceof Geometry.Geometry_Variant0 variant0) {
            return variant0.value();
        } else if (geometry instanceof Geometry.Geometry_Variant1 variant1) {
            return variant1.identifier();
        }
        return null;
    }

    private boolean extractUnitCube(Component comp, String geometryId) {
        // If no custom geometry, it's a unit cube
        return geometryId == null || geometryId.equals("minecraft:geometry.full_block");
    }

    private boolean extractHasCraftingTable(Component comp) {
        return comp.minecraft_craftingTable() != null;
    }

    private boolean extractHasTickComponent(Component comp) {
        return comp.minecraft_tick() != null;
    }

    private String extractLootTable(Component comp) {
        return comp.minecraft_loot();
    }

    private boolean extractHasCustomComponents(Component comp) {
        return comp.minecraft_customComponents() != null;
    }

    private boolean extractWaterloggable(Component comp) {
        if (comp.minecraft_liquidDetection() == null) {
            return false;
        }
        var detection = comp.minecraft_liquidDetection();
        if (detection.detectionRules() != null) {
            for (var rule : detection.detectionRules()) {
                if (rule.canContainLiquid() != null && rule.canContainLiquid()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean extractBreathable(Component comp) {
        // Blocks are breathable if they don't have collision or aren't solid
        if (comp.minecraft_collisionBox() != null) {
            var box = comp.minecraft_collisionBox();
            if (box instanceof CollisionBox.CollisionBox_Variant0 variant0) {
                return !variant0.value(); // false collision = breathable
            }
        }
        return !extractSolid(comp);
    }

    private boolean extractRedstoneConductor(Component comp) {
        if (comp.minecraft_redstoneConductivity() == null) {
            return false;
        }
        var conductivity = comp.minecraft_redstoneConductivity();
        return conductivity.redstoneConductor() != null && conductivity.redstoneConductor();
    }

    private int extractRedstonePower(Component comp) {
        // TODO: Implement redstone power extraction if there's a component for it
        return 0;
    }

    private boolean extractRequiresCorrectTool(Component comp) {
        // Check if any tool tier tags are present
        return comp.tag_minecraft_diamondTierDestructible() != null ||
               comp.tag_minecraft_ironTierDestructible() != null ||
               comp.tag_minecraft_stoneTierDestructible() != null ||
               comp.tag_minecraft_netheriteTierDestructible() != null;
    }

    private String extractPreferredTool(Component comp) {
        // Determine preferred tool from tags
        if (comp.tag_minecraft_isPickaxeItemDestructible() != null) return "pickaxe";
        if (comp.tag_minecraft_isAxeItemDestructible() != null) return "axe";
        if (comp.tag_minecraft_isShovelItemDestructible() != null) return "shovel";
        if (comp.tag_minecraft_isHoeItemDestructible() != null) return "hoe";
        if (comp.tag_minecraft_isSwordItemDestructible() != null) return "sword";
        if (comp.tag_minecraft_isShearsItemDestructible() != null) return "shears";
        if (comp.tag_minecraft_isMaceItemDestructible() != null) return "mace";
        return null;
    }

    private int extractToolTier(Component comp) {
        // Determine tool tier from tags (0=wood, 1=stone, 2=iron, 3=diamond, 4=netherite)
        if (comp.tag_minecraft_netheriteTierDestructible() != null) return 4;
        if (comp.tag_minecraft_diamondTierDestructible() != null) return 3;
        if (comp.tag_minecraft_ironTierDestructible() != null) return 2;
        if (comp.tag_minecraft_stoneTierDestructible() != null) return 1;
        return 0;
    }

    // Override Nukkit Block methods (based on ECProEntity signatures)

    @Override
    protected AxisAlignedBB recalculateBoundingBox() {
        if (definition == null || definition.collisionBox == null) {
            return recalculateCollisionBoundingBox();
        }
        SimpleAxisAlignedBB box = definition.collisionBox;
        return new SimpleAxisAlignedBB(
                this.x + box.getMinX(),
                this.y + box.getMinY(),
                this.z + box.getMinZ(),
                this.x + box.getMaxX(),
                this.y + box.getMaxY(),
                this.z + box.getMaxZ()
        );
    }

    @Override
    public String getName() {
        return definition != null ? definition.displayName : "Unknown Block";
    }

    @Override
    public float getHardness() {
        return definition != null ? definition.hardness : 0.0f;
    }

    @Override
    public float getResistance() {
        return definition != null ? definition.resistance : 0.0f;
    }

    @Override
    public int getLightLevel() {
        return definition != null ? definition.lightEmission : 0;
    }

    @Override
    public int getLightBlock() {
        return definition != null ? definition.lightDampening : 15;
    }

    @Override
    public float getFrictionFactor() {
        return definition != null ? definition.friction : 0.6f;
    }

    @Override
    public boolean canBeActivated() {
        return false;
    }

    /**
     * Register block states based on traits and custom states defined in the block definition.
     * This method is called by Nukkit during block initialization to set up the block's state properties.
     *
     * States include:
     * - Cardinal connections (for fence-like blocks)
     * - Cardinal direction (4 horizontal directions: north, south, east, west)
     * - Facing direction (6 directions including up/down)
     * - Block face (which face the block is attached to)
     * - Vertical half (top/bottom half)
     * - Custom states (defined in block JSON)
     */
    @Override
    protected void addStates() {
        tryInitBlockDefinition();

        if (definition == null) {
            return;
        }

        // Add trait states for cardinal connections (fence-like connections)
        if (definition.stateCardinalConnections) {
            addState(BlockStates.MINECRAFT_CONNECTION_EAST);
            addState(BlockStates.MINECRAFT_CONNECTION_NORTH);
            addState(BlockStates.MINECRAFT_CONNECTION_SOUTH);
            addState(BlockStates.MINECRAFT_CONNECTION_WEST);
        }

        // Add trait states for cardinal direction (4 horizontal directions)
        if (definition.stateCardinalDirection) {
            addState(BlockStates.MINECRAFT_CARDINAL_DIRECTION);
        }

        // Add trait states for facing direction (6 directions including up/down)
        if (definition.stateFacingDirection) {
            addState(BlockStates.MINECRAFT_FACING_DIRECTION);
        }

        // Add trait states for block face (which face the block is attached to)
        if (definition.stateBlockFace) {
            addState(BlockStates.MINECRAFT_BLOCK_FACE);
        }

        // Add trait states for vertical half (top/bottom)
        if (definition.stateVerticalHalf) {
            addState(BlockStates.MINECRAFT_VERTICAL_HALF);
        }

        // Add custom states from block definition
        addCustomStates();
    }

    /**
     * Add custom states defined in the block's JSON definition.
     * Supports integer ranges, string enums, and boolean states.
     *
     * Note: Custom block states are registered in the NBT during block registration (see EasecationRegistry).
     * The Nukkit BlockState system currently doesn't support dynamic registration of custom states
     * via the addState() method - it only works with predefined states from BlockStates class.
     *
     * TODO: Once EaseCation Nukkit adds support for dynamic BlockState creation
     * (IntBlockState, StringBlockState, BooleanBlockState constructors), implement this method
     * to register custom states at runtime.
     *
     * For now, custom states are handled via:
     * 1. NBT properties during block registration (EasecationRegistry)
     * 2. Block permutations for state-dependent behavior
     */
    private void addCustomStates() {
        // Custom states are already registered via NBT in EasecationRegistry.registerCustomBlock()
        // This method is reserved for future dynamic BlockState registration support

        int runtimeId = this.getId();
        BlockDef blockDef = BLOCK_DEF_REGISTRY.get(runtimeId);
        if (blockDef == null || blockDef.description() == null) {
            return;
        }

        Map<String, StatesValue> customStates = blockDef.description().states();
        if (customStates == null || customStates.isEmpty()) {
            return;
        }

        // Log custom states for debugging
        for (String stateName : customStates.keySet()) {
            // Debug: uncomment to trace custom state registration
            // System.out.println("[BlockDataDriven] Custom state defined: " + stateName + " for block " + blockDef.id());
        }
    }

    /**
     * Internal block definition holding all properties.
     * Expanded from 8 fields to match ECProEntity's complete implementation.
     */
    public static class BlockDefinition {
        // === Basic Identification ===
        public final String identifier;
        public final String displayName;

        // === Collision and Selection ===
        public final SimpleAxisAlignedBB collisionBox;
        public final SimpleAxisAlignedBB selectionBox;

        // === Visual Properties ===
        public final int mapColor; // RGB color for map display
        public final boolean solid; // Is the block solid/opaque
        public final boolean replaceable; // Can be replaced by other blocks
        public final String geometryIdentifier; // Custom geometry model ID
        public final boolean unitCube; // Is a full unit cube (no custom geometry)

        // === Physical Properties ===
        public final float hardness; // Mining time in seconds
        public final float resistance; // Explosion resistance
        public final int flameOdds; // Flammable catch chance modifier
        public final int burnOdds; // Flammable destroy chance modifier
        public final float friction; // Block friction (0.0-1.0)

        // === Light Properties ===
        public final int lightEmission; // Light level emitted (0-15)
        public final int lightDampening; // Light absorption (0-15)

        // === Movement and Placement ===
        public final int movable; // Piston movement flags (bit field)
        public final boolean pottable; // Can be placed in flower pot
        public final boolean placementFilter; // Has placement restrictions
        public final int allowedPlacementFaces; // Allowed placement faces (bit field)

        // === Block States/Traits ===
        public final boolean stateCardinalDirection; // Has cardinal_direction state
        public final boolean stateFacingDirection; // Has facing_direction state
        public final boolean stateBlockFace; // Has block_face state
        public final boolean stateVerticalHalf; // Has vertical_half state
        public final boolean stateCardinalConnections; // Has cardinal_connections states

        // === Functional Components ===
        public final boolean hasCraftingTable; // Is a crafting table
        public final boolean hasTickComponent; // Has periodic tick events
        public final String lootTable; // Loot table path
        public final boolean hasCustomComponents; // Uses custom components

        // === Liquid and Environment ===
        public final boolean waterloggable; // Can contain water (waterlogging)
        public final boolean breathable; // Is breathable (not suffocating)

        // === Redstone and Circuit ===
        public final boolean redstoneConductor; // Conducts redstone signal
        public final int redstonePower; // Redstone power level (0-15)

        // === Tool and Mining ===
        public final boolean requiresCorrectTool; // Needs proper tool for drops
        public final String preferredTool; // Preferred tool type (pickaxe, axe, etc.)
        public final int toolTier; // Minimum tool tier (0=wood, 1=stone, etc.)

        // === Custom States ===
        // Note: Custom states are managed by BlockDataDriven's blockState field

        public BlockDefinition(String identifier, String displayName,
                             SimpleAxisAlignedBB collisionBox, SimpleAxisAlignedBB selectionBox,
                             int mapColor, boolean solid, boolean replaceable,
                             String geometryIdentifier, boolean unitCube,
                             float hardness, float resistance,
                             int flameOdds, int burnOdds, float friction,
                             int lightEmission, int lightDampening,
                             int movable, boolean pottable,
                             boolean placementFilter, int allowedPlacementFaces,
                             boolean stateCardinalDirection, boolean stateFacingDirection,
                             boolean stateBlockFace, boolean stateVerticalHalf,
                             boolean stateCardinalConnections,
                             boolean hasCraftingTable, boolean hasTickComponent,
                             String lootTable, boolean hasCustomComponents,
                             boolean waterloggable, boolean breathable,
                             boolean redstoneConductor, int redstonePower,
                             boolean requiresCorrectTool, String preferredTool, int toolTier) {
            this.identifier = identifier;
            this.displayName = displayName;
            this.collisionBox = collisionBox;
            this.selectionBox = selectionBox;
            this.mapColor = mapColor;
            this.solid = solid;
            this.replaceable = replaceable;
            this.geometryIdentifier = geometryIdentifier;
            this.unitCube = unitCube;
            this.hardness = hardness;
            this.resistance = resistance;
            this.flameOdds = flameOdds;
            this.burnOdds = burnOdds;
            this.friction = friction;
            this.lightEmission = lightEmission;
            this.lightDampening = lightDampening;
            this.movable = movable;
            this.pottable = pottable;
            this.placementFilter = placementFilter;
            this.allowedPlacementFaces = allowedPlacementFaces;
            this.stateCardinalDirection = stateCardinalDirection;
            this.stateFacingDirection = stateFacingDirection;
            this.stateBlockFace = stateBlockFace;
            this.stateVerticalHalf = stateVerticalHalf;
            this.stateCardinalConnections = stateCardinalConnections;
            this.hasCraftingTable = hasCraftingTable;
            this.hasTickComponent = hasTickComponent;
            this.lootTable = lootTable;
            this.hasCustomComponents = hasCustomComponents;
            this.waterloggable = waterloggable;
            this.breathable = breathable;
            this.redstoneConductor = redstoneConductor;
            this.redstonePower = redstonePower;
            this.requiresCorrectTool = requiresCorrectTool;
            this.preferredTool = preferredTool;
            this.toolTier = toolTier;
        }
    }
}
