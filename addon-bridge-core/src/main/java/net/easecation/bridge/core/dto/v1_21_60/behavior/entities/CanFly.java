package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;

/* Marks the entity as being able to fly, the pathfinder won't be restricted to paths where a solid block is required underneath it. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = CanFly.Deserializer.class)
public sealed interface CanFly {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record CanFly_Variant1(
        @JsonValue Boolean value
    ) implements CanFly {
        @JsonCreator
        public static CanFly_Variant1 of(Boolean value) {
            return new CanFly_Variant1(value);
        }
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<CanFly> {
        @Override
        public CanFly deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                    nodeParser.nextToken();
                    return ctxt.readValue(nodeParser, CanFly_Variant1.class);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize CanFly: no matching variant found");
        }
    }
}
