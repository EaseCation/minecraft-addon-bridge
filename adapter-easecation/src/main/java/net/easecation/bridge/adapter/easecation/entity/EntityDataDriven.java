package net.easecation.bridge.adapter.easecation.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.entity.data.FloatEntityData;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ProtocolInfo;
import net.easecation.bridge.core.dto.v1_21_60.behavior.entities.Components;
import net.easecation.bridge.core.dto.v1_21_60.behavior.entities.Range_a_B_;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data-driven custom entity implementation for EaseCation Nukkit.
 * Based on ECProEntity's EntityDataDriven implementation.
 */
public class EntityDataDriven extends EntityCreature {

    // Static registry to map identifier to Components
    public static final Map<String, Components> ID_COMPONENTS_MAP = new ConcurrentHashMap<>();

    private String identifier;
    private Components components;
    private float width = 0.6f;
    private float height = 1.8f;

    // Component state fields
    private boolean hasGravity = true;
    private boolean hasCollision = true;
    private boolean isPushable = true;
    private boolean isPushableByPiston = true;
    private boolean isRideable = false;
    private int seatCount = 0;

    public EntityDataDriven(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    /**
     * Register components for a given entity identifier.
     */
    public static void registerComponents(String identifier, Components components) {
        ID_COMPONENTS_MAP.put(identifier, components);
    }

    @Override
    public int getNetworkId() {
        // Return network ID for custom entity
        // EaseCation should handle custom entity IDs
        return 0;
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        // Read identifier from NBT
        this.identifier = this.namedTag.getString("identifier");
        if (this.identifier == null || this.identifier.isEmpty()) {
            throw new RuntimeException("EntityDataDriven: No identifier found in NBT");
        }

        // Load components
        this.components = ID_COMPONENTS_MAP.get(this.identifier);
        if (this.components == null) {
            throw new RuntimeException("EntityDataDriven: Components not found for identifier " + this.identifier);
        }

        // Initialize components
        initComponents();
    }

    private void initComponents() {
        if (components == null) {
            return;
        }

        // Movement speed - minecraft:movement is an Attribute
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
                this.health = healthValue.floatValue();
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
            // Update entity data properties
            this.recalculateBoundingBox();
            this.setDataProperty(new FloatEntityData(DATA_BOUNDING_BOX_WIDTH, this.width));
            this.setDataProperty(new FloatEntityData(DATA_BOUNDING_BOX_HEIGHT, this.height));
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
            // pushTowardsClosestSpace is handled by server automatically
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
        if (components.minecraft_variant != null) {
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
            // Set rideable flag for entity using entity data
            // Note: Seat positions, interact text handled by client-side behavior pack
        }

        // Flying speed - minecraft:flying_speed
        // Note: Base EntityCreature doesn't have direct flying speed support

        // Can fly - minecraft:can_fly
        // Note: Requires custom flight implementation

        // Can climb - minecraft:can_climb
        // Note: Requires custom climbing implementation

        // Is baby - minecraft:is_baby
        if (components.minecraft_isBaby != null) {
            // Set baby flag via entity data
            // Note: Baby state is primarily handled client-side via behavior pack
        }

        // Breathable - minecraft:breathable
        // Note: Requires custom breathing implementation
    }

    /**
     * Extract a numeric value from Range_a_B_ sealed interface.
     */
    private Double extractRangeValue(Range_a_B_ range) {
        if (range == null) {
            return null;
        }

        if (range instanceof Range_a_B_.Range_a_B__Variant0 variant0) {
            return variant0.value();
        } else if (range instanceof Range_a_B_.Range_a_B__Variant2 variant2) {
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

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public float getGravity() {
        // Check for minecraft:physics component
        if (!this.hasGravity) {
            return 0.0f;
        }
        return super.getGravity();
    }

    @Override
    public boolean canCollideWith(cn.nukkit.entity.Entity entity) {
        // Check for minecraft:physics component (hasCollision)
        if (!this.hasCollision) {
            return false;
        }
        return super.canCollideWith(entity);
    }

    @Override
    public boolean onInteract(Player player, Item item) {
        // TODO: Handle minecraft:interact component
        return super.onInteract(player, item);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        // Handle minecraft:damage_sensor component
        // DamageSensor allows custom damage filtering and event triggers
        // The triggers configuration is complex and requires Molang evaluation
        // For now, delegate to parent implementation
        // Full implementation would involve:
        // 1. Evaluating trigger conditions against the damage source
        // 2. Modifying damage amount based on filters
        // 3. Firing custom events defined in triggers
        if (components != null && components.minecraft_damageSensor != null) {
            // Future: Implement trigger evaluation and custom damage handling
        }
        return super.attack(source);
    }

    public Components getComponents() {
        return components;
    }

    public String getCustomIdentifier() {
        return identifier;
    }
}
