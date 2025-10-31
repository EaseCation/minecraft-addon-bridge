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

    // Component state fields
    private net.easecation.bridge.core.dto.entity.v1_21_60.Components components;
    private float width = 0.6f;
    private float height = 1.8f;
    private boolean hasGravity = true;
    private boolean hasCollision = true;
    private boolean isPushable = true;
    private boolean isPushableByPiston = true;
    private boolean fireProof = false;
    private boolean isRideable = false;
    private int seatCount = 0;
    private float movementSpeed = 0.1f;

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

        // Load components from EntityDef
        EntityDef entityDef = ENTITY_DEF_REGISTRY.get(identifier);
        if (entityDef != null) {
            this.components = entityDef.components();
        }

        // Initialize components (must be called after EntityDefinition is built)
        initComponents();
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
        // Return height from collision_box component if set, otherwise default
        return this.height;
    }

    @Override
    public float getWidth() {
        // Return width from collision_box component if set, otherwise default
        return this.width;
    }

    @Override
    public float getLength() {
        return getWidth();
    }

    /**
     * Initialize entity components from EntityDef.
     * Reads and applies all supported entity components to configure entity behavior.
     */
    private void initComponents() {
        if (components == null) {
            return;
        }

        // Movement speed - minecraft:movement
        if (components.minecraft_movement != null) {
            Double value = extractRangeValue(components.minecraft_movement.value());
            if (value != null) {
                this.movementSpeed = value.floatValue();
            }
        }

        // Scale - minecraft:scale
        if (components.minecraft_scale != null && components.minecraft_scale.value() != null) {
            this.setScale(components.minecraft_scale.value().floatValue());
        }

        // Health - minecraft:health
        if (components.minecraft_health != null) {
            var health = components.minecraft_health;
            if (health.max() != null && health.max() > 0) {
                this.setMaxHealth((int) health.max().doubleValue());
            }
            Double healthValue = extractRangeValue(health.value());
            if (healthValue != null && healthValue > 0) {
                this.setHealth(healthValue.floatValue());
            }
        }

        // Collision box - minecraft:collision_box
        if (components.minecraft_collisionBox != null) {
            var collisionBox = components.minecraft_collisionBox;
            if (collisionBox.width() != null) {
                this.width = collisionBox.width().floatValue();
            }
            if (collisionBox.height() != null) {
                this.height = collisionBox.height().floatValue();
            }
            // MOT Entity needs bounding box recalculation
            this.recalculateBoundingBox();
        }

        // Fire immune - minecraft:fire_immune
        if (components.minecraft_fireImmune != null) {
            this.fireProof = true;
        }

        // Physics - minecraft:physics
        if (components.minecraft_physics != null) {
            var physics = components.minecraft_physics;
            if (physics.hasGravity() != null) {
                this.hasGravity = physics.hasGravity();
            }
            if (physics.hasCollision() != null) {
                this.hasCollision = physics.hasCollision();
            }
        }

        // Pushable - minecraft:pushable
        if (components.minecraft_pushable != null) {
            var pushable = components.minecraft_pushable;
            if (pushable.isPushable() != null) {
                this.isPushable = pushable.isPushable();
            }
            if (pushable.isPushableByPiston() != null) {
                this.isPushableByPiston = pushable.isPushableByPiston();
            }
        }

        // Variant - minecraft:variant
        if (components.minecraft_variant != null && components.minecraft_variant.value() != null) {
            this.setDataProperty(new cn.nukkit.entity.data.IntEntityData(DATA_VARIANT, components.minecraft_variant.value()));
        }

        // Mark Variant - minecraft:mark_variant
        if (components.minecraft_markVariant != null && components.minecraft_markVariant.value() != null) {
            this.setDataProperty(new cn.nukkit.entity.data.IntEntityData(DATA_MARK_VARIANT, components.minecraft_markVariant.value()));
        }

        // Skin ID - minecraft:skin_id
        if (components.minecraft_skinId != null && components.minecraft_skinId.value() != null) {
            this.setDataProperty(new cn.nukkit.entity.data.IntEntityData(DATA_SKIN_ID, components.minecraft_skinId.value()));
        }

        // Rideable - minecraft:rideable
        if (components.minecraft_rideable != null) {
            var rideable = components.minecraft_rideable;
            this.isRideable = true;
            if (rideable.seatCount() != null) {
                this.seatCount = rideable.seatCount();
            }
            // Note: Seat positions and interact text are handled by client-side behavior pack
        }
    }

    /**
     * Extract a numeric value from Range_a_B sealed interface.
     * This handles the different variants of the Range type used in entity attributes.
     */
    private Double extractRangeValue(net.easecation.bridge.core.dto.entity.v1_21_60.Range_a_B range) {
        if (range == null) {
            return null;
        }

        if (range instanceof net.easecation.bridge.core.dto.entity.v1_21_60.Range_a_B.Range_a_B_Variant0 variant0) {
            return variant0.value();
        } else if (range instanceof net.easecation.bridge.core.dto.entity.v1_21_60.Range_a_B.Range_a_B_Variant2 variant2) {
            // For range, use min value
            if (variant2.rangeMin() != null) {
                return variant2.rangeMin();
            }
            if (variant2.rangeMax() != null) {
                return variant2.rangeMax();
            }
        }

        return null;
    }

}
