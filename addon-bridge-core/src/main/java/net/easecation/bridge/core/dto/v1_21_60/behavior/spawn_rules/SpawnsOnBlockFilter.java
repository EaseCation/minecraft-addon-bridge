package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.List;

/* This component allows an entity to spawn on a particular block. It includes a string or array of strings for the block they may spawn on. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = SpawnsOnBlockFilter.Deserializer.class)
public sealed interface SpawnsOnBlockFilter {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record SpawnsOnBlockFilter_Variant0(
        @JsonValue String value
    ) implements SpawnsOnBlockFilter {
        @JsonCreator
        public static SpawnsOnBlockFilter_Variant0 of(String value) {
            return new SpawnsOnBlockFilter_Variant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record SpawnsOnBlockFilter_Variant1(
        List<String> value
    ) implements SpawnsOnBlockFilter {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<SpawnsOnBlockFilter> {
        @Override
        public SpawnsOnBlockFilter deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                    nodeParser.nextToken();
                    return ctxt.readValue(nodeParser, SpawnsOnBlockFilter_Variant0.class);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, SpawnsOnBlockFilter_Variant1.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize SpawnsOnBlockFilter: no matching variant found");
        }
    }
}
