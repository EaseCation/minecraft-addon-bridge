package net.easecation.bridge.adapter.mot.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.custom.CustomEntity;
import cn.nukkit.entity.custom.EntityDefinition;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import net.easecation.bridge.core.EntityDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data-driven custom entity implementation for MOT Nukkit.
 * Loads entity properties dynamically from EntityDef based on Bedrock behavior pack format.
 *
 * <p>MOT uses Entity + CustomEntity interface with EntityDefinition for registration.</p>
 */
public class EntityDataDriven extends Entity implements CustomEntity {

    // Static registry to map identifier to EntityDef
    private static final Map<String, EntityDef> ENTITY_DEF_REGISTRY = new ConcurrentHashMap<>();

    // Static registry to map identifier to EntityDefinition
    private static final Map<String, EntityDefinition> DEFINITION_CACHE = new ConcurrentHashMap<>();

    private EntityDefinition definition;

    public EntityDataDriven(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    /**
     * Register an EntityDef for a given identifier.
     */
    public static void registerEntityDef(String identifier, EntityDef entityDef) {
        ENTITY_DEF_REGISTRY.put(identifier, entityDef);
    }

    /**
     * Register a pre-built EntityDefinition for a given identifier.
     */
    public static void registerDefinition(String identifier, EntityDefinition definition) {
        DEFINITION_CACHE.put(identifier, definition);
    }

    /**
     * Get the entity identifier. This method should be overridden by dynamically generated subclasses
     * to return their specific identifier.
     *
     * @return The entity identifier
     */
    protected String getEntityIdentifier() {
        throw new UnsupportedOperationException(
            "getEntityIdentifier() must be overridden by dynamically generated subclass");
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        // Get identifier from the dynamically generated subclass
        String identifier = getEntityIdentifier();

        // Get or build EntityDefinition
        this.definition = DEFINITION_CACHE.get(identifier);
        if (this.definition == null) {
            EntityDef entityDef = ENTITY_DEF_REGISTRY.get(identifier);
            if (entityDef == null) {
                throw new RuntimeException("EntityDataDriven: EntityDef not found for identifier '" + identifier + "'");
            }

            // Build EntityDefinition (using the actual generated class)
            this.definition = EntityDefinitionBuilder.build(entityDef, this.getClass());
            DEFINITION_CACHE.put(identifier, this.definition);
        }
    }

    /**
     * MOT required method: return the EntityDefinition for this entity.
     */
    @Override
    public EntityDefinition getEntityDefinition() {
        if (definition != null) {
            return definition;
        }

        // Fallback: try to get from cache using identifier from subclass
        try {
            String identifier = getEntityIdentifier();
            definition = DEFINITION_CACHE.get(identifier);
            if (definition != null) {
                return definition;
            }
        } catch (UnsupportedOperationException e) {
            // getEntityIdentifier() not overridden, definition must be initialized in initEntity
        }

        throw new RuntimeException("EntityDataDriven: EntityDefinition not initialized. Make sure initEntity() is called.");
    }

    @Override
    public int getNetworkId() {
        return getEntityDefinition().getRuntimeId();
    }

    @Override
    public float getHeight() {
        // Get from EntityDef if available
        try {
            String identifier = getEntityIdentifier();
            EntityDef entityDef = ENTITY_DEF_REGISTRY.get(identifier);
            if (entityDef != null && entityDef.description() != null &&
                entityDef.description().properties() != null) {
                // Try to extract height from properties if defined
                // For now, return default
            }
        } catch (UnsupportedOperationException e) {
            // getEntityIdentifier() not available yet
        }
        return 1.8f; // Default height
    }

    @Override
    public float getWidth() {
        // Get from EntityDef if available
        return 0.6f; // Default width
    }

    @Override
    public float getLength() {
        return getWidth();
    }

}
