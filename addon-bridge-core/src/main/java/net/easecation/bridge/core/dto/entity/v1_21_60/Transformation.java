package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines an entity's transformation from the current definition into another */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Transformation(
    /* List of components to add to the entity after the transformation. */
    @JsonProperty("add") @Nullable Add add,
    /* Sound to play when the transformation starts. */
    @JsonProperty("begin_transform_sound") @Nullable String beginTransformSound,
    /* Defines the properties of the delay for the transformation. */
    @JsonProperty("delay") @Nullable Object delay,
    /* Cause the entity to drop all equipment upon transformation. */
    @JsonProperty("drop_equipment") @Nullable Boolean dropEquipment,
    /* Cause the entity to drop all items in inventory upon transformation. */
    @JsonProperty("drop_inventory") @Nullable Boolean dropInventory,
    /* Entity Definition that this entity will transform into. */
    @JsonProperty("into") @Nullable String into,
    /* If this entity has trades and has leveled up, it should maintain that level after transformation. */
    @JsonProperty("keep_level") @Nullable Boolean keepLevel,
    /* If this entity is owned by another entity, it should remain owned after transformation. */
    @JsonProperty("keep_owner") @Nullable Boolean keepOwner,
    /* Cause the entity to keep equipment after going through transformation. */
    @JsonProperty("preserve_equipment") @Nullable Boolean preserveEquipment,
    /* Sound to play when the entity is done transforming. */
    @JsonProperty("transformation_sound") @Nullable String transformationSound
) {
    
        /* List of components to add to the entity after the transformation. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Add(
            /* Names of component groups to add. */
            @JsonProperty("component_groups") @Nullable List<String> componentGroups
        ) {
        }
}
