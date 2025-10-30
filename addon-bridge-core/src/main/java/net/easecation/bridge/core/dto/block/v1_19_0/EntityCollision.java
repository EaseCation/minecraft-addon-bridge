package net.easecation.bridge.core.dto.block.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;

/* Can only be set to false or an object, it disables the collision of the block with entities. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = EntityCollision.Deserializer.class)
public sealed interface EntityCollision {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntityCollision_Variant0(
        @JsonValue Boolean value
    ) implements EntityCollision {
        @JsonCreator
        public static EntityCollision_Variant0 of(Boolean value) {
            return new EntityCollision_Variant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntityCollision_Variant1(
        /* Minimal position Bounds of the collision box. */
        @JsonProperty("origin") @Nullable List<Double> origin,
        /* Size of each side of the box of the component. */
        @JsonProperty("size") @Nullable List<Double> size
    ) implements EntityCollision {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<EntityCollision> {
        @Override
        public EntityCollision deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    Boolean value = node.asBoolean();
                    return EntityCollision_Variant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, EntityCollision_Variant1.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize EntityCollision: no matching variant found");
        }
    }
}
