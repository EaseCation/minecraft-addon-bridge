package net.easecation.bridge.core.dto.v1_21_60.behavior.lighting;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.List;

/* The color of the light emitted by the block, in RGB format or hex format. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = LightingColor.Deserializer.class)
public sealed interface LightingColor {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record LightingColor_Variant0(
        @JsonValue String value
    ) implements LightingColor {
        @JsonCreator
        public static LightingColor_Variant0 of(String value) {
            return new LightingColor_Variant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record LightingColor_Variant1(
        List<Integer> value
    ) implements LightingColor {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<LightingColor> {
        @Override
        public LightingColor deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                    nodeParser.nextToken();
                    return ctxt.readValue(nodeParser, LightingColor_Variant0.class);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, LightingColor_Variant1.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize LightingColor: no matching variant found");
        }
    }
}
