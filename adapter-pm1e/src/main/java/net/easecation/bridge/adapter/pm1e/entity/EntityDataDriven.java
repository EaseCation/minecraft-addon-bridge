package net.easecation.bridge.adapter.pm1e.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.custom.CustomEntity;
import cn.nukkit.entity.custom.EntityDefinition;
import cn.nukkit.entity.custom.EntityManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import net.easecation.bridge.core.EntityDef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data-driven custom entity implementation for PM1E Nukkit.
 * Loads entity properties dynamically from EntityDef based on Bedrock behavior pack format.
 *
 * <p>PM1E uses Entity + CustomEntity interface with EntityDefinition for registration.</p>
 */
public class EntityDataDriven extends Entity implements CustomEntity {

    private static final Map<String, EntityDef> ENTITY_DEF_REGISTRY = new ConcurrentHashMap<>();
    private static final Map<String, EntityDefinition> DEFINITION_CACHE = new ConcurrentHashMap<>();

    private EntityDefinition definition;

    public EntityDataDriven(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
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

    public static void registerEntityDef(String identifier, EntityDef entityDef) {
        ENTITY_DEF_REGISTRY.put(identifier, entityDef);
    }

    public static void registerDefinition(String identifier, EntityDefinition definition) {
        DEFINITION_CACHE.put(identifier, definition);
    }

    public static EntityDef getEntityDef(String identifier) {
        return ENTITY_DEF_REGISTRY.get(identifier);
    }

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
        return EntityManager.get().getRuntimeId(getEntityIdentifier());
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        // Get identifier from the dynamically generated subclass
        String identifier = getEntityIdentifier();

        // Get or build EntityDefinition
        this.definition = DEFINITION_CACHE.get(identifier);
        if (this.definition == null) {
            throw new RuntimeException("EntityDataDriven: EntityDefinition not found for identifier '" + identifier + "'");
        }

        // Load and apply entity components
        EntityDef entityDef = ENTITY_DEF_REGISTRY.get(identifier);
        if (entityDef != null && entityDef.components() != null) {
            // Fix: Use field access instead of method call
            var health = entityDef.components().minecraft_health;
            if (health != null) {
                // health.max() returns Double, convert to Integer
                Integer healthValue = health.max() != null ? health.max().intValue() : 20;  // Default to 20
                this.setMaxHealth(healthValue);
                this.setHealth(healthValue.floatValue());
            }

            // Fix: Use field access instead of method call, CollisionBox fields are private, use getters
            var collisionBox = entityDef.components().minecraft_collisionBox;
            if (collisionBox != null) {
                if (collisionBox.width() != null) {
                    this.setDataProperty(new cn.nukkit.entity.data.FloatEntityData(
                        Entity.DATA_BOUNDING_BOX_WIDTH, collisionBox.width().floatValue()));
                }
                if (collisionBox.height() != null) {
                    this.setDataProperty(new cn.nukkit.entity.data.FloatEntityData(
                        Entity.DATA_BOUNDING_BOX_HEIGHT, collisionBox.height().floatValue()));
                }
            }
        }
    }
}
