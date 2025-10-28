package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

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

/* Defines the area of the block that is selected by the player's cursor. If set to true, default values are used. If set to false, this block is not selectable by the player's cursor. If this component is omitted, default values are used. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = SelectionBox.Deserializer.class)
public sealed interface SelectionBox {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record SelectionBox_Variant0(
        @JsonValue Boolean value
    ) implements SelectionBox {
        @JsonCreator
        public static SelectionBox_Variant0 of(Boolean value) {
            return new SelectionBox_Variant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record SelectionBox_Variant1(
        /* Minimal position of the bounds of the selection box. "origin" is specified as [x, y, z] and must be in the range (-8, 0, -8) to (8, 16, 8), inclusive. */
        @JsonProperty("origin") @Nullable List<Double> origin,
        /* Size of each side of the selection box. Size is specified as [x, y, z]. "origin" + "size" must be in the range (-8, 0, -8) to (8, 16, 8), inclusive. */
        @JsonProperty("size") @Nullable List<Double> size
    ) implements SelectionBox {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<SelectionBox> {
        @Override
        public SelectionBox deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                    nodeParser.nextToken();
                    return ctxt.readValue(nodeParser, SelectionBox_Variant0.class);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, SelectionBox_Variant1.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize SelectionBox: no matching variant found");
        }
    }
}
