package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = BcTransformation.Deserializer.class)
public sealed interface BcTransformation {
    /* String name of a Biome. */
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record BcTransformation_Biome(
        /* String name of a Biome. */
        @JsonValue String value
    ) implements BcTransformation {
        @JsonCreator
        public static BcTransformation_Biome of(String value) {
            return new BcTransformation_Biome(value);
        }
    }
    /* Array of any size. If an array, each entry can be a Biome name string, or an array of size 2, where the first entry is a Biome name and the second entry is a positive integer representing how that Biome is weighted against other entries. If no weight is provided, a weight of 1 is used.. */
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record BcTransformation_BlockReference(
        /* Array of any size. If an array, each entry can be a Biome name string, or an array of size 2, where the first entry is a Biome name and the second entry is a positive integer representing how that Biome is weighted against other entries. If no weight is provided, a weight of 1 is used.. */
        List<Object> value
    ) implements BcTransformation {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<BcTransformation> {
        @Override
        public BcTransformation deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                    nodeParser.nextToken();
                    return ctxt.readValue(nodeParser, BcTransformation_Biome.class);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, BcTransformation_BlockReference.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize BcTransformation: no matching variant found");
        }
    }
}
