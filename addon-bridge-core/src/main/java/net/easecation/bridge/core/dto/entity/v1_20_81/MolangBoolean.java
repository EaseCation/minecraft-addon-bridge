package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;

/* The minecraft molang definition that results in a boolean. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = MolangBoolean.Deserializer.class)
public sealed interface MolangBoolean {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record MolangBoolean_Variant0(
        @JsonValue String value
    ) implements MolangBoolean {
        @JsonCreator
        public static MolangBoolean_Variant0 of(String value) {
            return new MolangBoolean_Variant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record MolangBoolean_Variant1(
        @JsonValue Boolean value
    ) implements MolangBoolean {
        @JsonCreator
        public static MolangBoolean_Variant1 of(Boolean value) {
            return new MolangBoolean_Variant1(value);
        }
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<MolangBoolean> {
        @Override
        public MolangBoolean deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    String value = node.asText();
                    return MolangBoolean_Variant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    Boolean value = node.asBoolean();
                    return MolangBoolean_Variant1.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize MolangBoolean: no matching variant found");
        }
    }
}
