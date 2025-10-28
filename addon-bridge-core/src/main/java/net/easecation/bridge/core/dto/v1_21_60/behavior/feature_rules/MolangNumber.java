package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;

/* The minecraft molang definition that results in a float. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = MolangNumber.Deserializer.class)
public sealed interface MolangNumber {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record MolangNumber_Variant0(
        @JsonValue String value
    ) implements MolangNumber {
        @JsonCreator
        public static MolangNumber_Variant0 of(String value) {
            return new MolangNumber_Variant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record MolangNumber_Variant1(
        @JsonValue Double value
    ) implements MolangNumber {
        @JsonCreator
        public static MolangNumber_Variant1 of(Double value) {
            return new MolangNumber_Variant1(value);
        }
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<MolangNumber> {
        @Override
        public MolangNumber deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                    nodeParser.nextToken();
                    return ctxt.readValue(nodeParser, MolangNumber_Variant0.class);
                } catch (Exception e) {
                    // Try next variant
                }
                try {
                    com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                    nodeParser.nextToken();
                    return ctxt.readValue(nodeParser, MolangNumber_Variant1.class);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize MolangNumber: no matching variant found");
        }
    }
}
